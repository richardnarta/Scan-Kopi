package com.android.scankopi.presentation.ui.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.android.scankopi.R
import com.android.scankopi.data.src.local.Dummy.getRandomDummyTest
import com.android.scankopi.data.src.local.entity.TestResultEntity
import com.android.scankopi.databinding.ActivityScanBinding
import com.android.scankopi.helper.Cache.clearTempImages
import com.android.scankopi.helper.Constant.CACHE_IMAGE_PREFIX
import com.android.scankopi.helper.CustomToast
import com.android.scankopi.helper.Util.getTimeStamp
import com.android.scankopi.helper.bitmapToUri
import com.android.scankopi.helper.dpToPx
import com.android.scankopi.helper.invisible
import com.android.scankopi.helper.visible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ScanActivity : AppCompatActivity() {
    private var _binding: ActivityScanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScanViewModel by viewModels()

    private var imageCapture: ImageCapture? = null
    private var imageUri: Uri? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var cameraFrame: PreviewView

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private var firstShutter = true

    private var cameraFocusFrame: View? = null

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var customToast: CustomToast

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                startCamera()
            } else {
                customToast.showToast(getString(R.string.permission_denied))
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityScanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        customToast = CustomToast(this)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }

        setupBottomSheet()

        binding.apply {
            cameraFrame = previewView
        }

        observeAction()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun observeAction() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bottomSheetStatus()) {
                    finish()
                } else {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        })

        binding.btnCamera.setOnClickListener {
            if (bottomSheetStatus()) {
                takePhoto()
                firstShutter = false
                binding.btnCamera.invisible()
            }
        }

        binding.btnBack.setOnClickListener {
            if (bottomSheetStatus()) {
                handler.removeCallbacksAndMessages(null)
                finish()
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        binding.svResult.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    if (binding.svResult.scrollY == 0) {
                        binding.sheetTestResult.requestDisallowInterceptTouchEvent(false)
                    } else {
                        binding.sheetTestResult.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
            false
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val resolutionSelector = ResolutionSelector.Builder()
                .setResolutionStrategy(
                    ResolutionStrategy(Size(1920, 1080), ResolutionStrategy.FALLBACK_RULE_CLOSEST_LOWER)
                )
                .setAspectRatioStrategy(
                    AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY).build()

            val preview = Preview.Builder()
                .setResolutionSelector(resolutionSelector)
                .build()
                .also {
                    it.surfaceProvider = cameraFrame.surfaceProvider
                }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setResolutionSelector(resolutionSelector)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider?.unbindAll()
                val camera = cameraProvider!!.bindToLifecycle(
                    this@ScanActivity, cameraSelector, preview, imageCapture
                )
                setupZoomControls(camera)
            } catch (exc: Exception) {
                cameraProvider?.apply {
                    cameraProvider?.unbindAll()
                    cameraProvider = null
                }
                startCamera()
                customToast.showToast(getString(R.string.error_message))
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val cacheDir = File(cacheDir, "scankopi")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        val photoFile = File(cacheDir, "$CACHE_IMAGE_PREFIX${getTimeStamp()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    if (cameraProvider != null) {
                        cameraProvider?.unbindAll()
                        cameraProvider = null
                    }

                    binding.lottieScan.visible()

                    try {
                        Glide.with(this@ScanActivity)
                            .asBitmap()
                            .load(photoFile)
                            .into(object : CustomTarget<Bitmap>(){
                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: Transition<in Bitmap>?
                                ) {
                                    val cropRect = calculateCropRect(resource, cameraFrame)
                                    val croppedBitmap = Bitmap.createBitmap(
                                        resource,
                                        cropRect.left,
                                        cropRect.top,
                                        cropRect.width(),
                                        cropRect.height())

                                    imageUri = croppedBitmap.bitmapToUri(this@ScanActivity)
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {}
                            })
                    } catch (e: Exception) {
                        customToast.showToast(getString(R.string.error_message))
                    }

                    handler.postDelayed({
                        binding.lottieScan.invisible()
                        showBottomSheet()
                    }, 3000)
                }

                override fun onError(exception: ImageCaptureException) {
                    cameraProvider?.apply {
                        cameraProvider?.unbindAll()
                        cameraProvider = null
                    }
                    startCamera()
                    customToast.showToast(getString(R.string.error_message))
                }
            })
    }

    @OptIn(ExperimentalCamera2Interop::class)
    private fun setupZoomControls(camera: Camera) {
        val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                removeCameraFocusFrame()
                val currentZoomRatio = camera.cameraInfo.zoomState.value?.zoomRatio ?: 1F
                val delta = detector.scaleFactor
                camera.cameraControl.setZoomRatio(currentZoomRatio * delta)
                return true
            }
        }

        val scaleGestureDetector = ScaleGestureDetector(this, listener)
        binding.previewView.setOnTouchListener { view, event ->
            scaleGestureDetector.onTouchEvent(event)

            if (event.action == MotionEvent.ACTION_DOWN) {
                val factory = binding.previewView.meteringPointFactory
                val point = factory.createPoint(event.x, event.y)
                val action = FocusMeteringAction.Builder(point).build()

                removeCameraFocusFrame()
                val previewLocation = IntArray(2)
                binding.previewView.getLocationOnScreen(previewLocation)
                cameraFocusFrame = layoutInflater.inflate(
                    R.layout.camera_focus_frame,
                    binding.root,
                    false).apply {
                    this.x = (event.x + previewLocation[0]) - 50.dpToPx(resources)
                    this.y = (event.y + previewLocation[1]) - 100.dpToPx(resources)
                }

                if (bottomSheetStatus()) {
                    binding.root.addView(cameraFocusFrame)
                }

                val future = camera.cameraControl.startFocusAndMetering(action)

                future.addListener({
                    try {
                        if (bottomSheetStatus()) {
                            val result = future.get()
                            if (result.isFocusSuccessful) {
                                removeCameraFocusFrame()
                            }
                        }
                    } catch (e: java.util.concurrent.ExecutionException) {
                        Log.w("Warning", "Focus operation was canceled")
                    } catch (e: Exception) {
                        customToast.showToast(getString(R.string.error_message))
                    }
                }, ContextCompat.getMainExecutor(this@ScanActivity))
            }

            if (event.action == MotionEvent.ACTION_UP) {
                view.performClick()
            }

            return@setOnTouchListener true
        }
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.sheetTestResult)

        bottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_HIDDEN
            peekHeight = 0
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN && cameraProvider == null) {
                    clearTempImages(this@ScanActivity)
                    startCamera()
                    if (binding.btnSave.isEnabled) {
                        imageUri?.let { deleteImage(it) }
                    }
                    binding.btnSave.isEnabled = true
                    binding.btnCamera.visible()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun bottomSheetStatus(): Boolean {
        return ((firstShutter && bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
                or (!firstShutter && bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN))
    }

    private fun showBottomSheet() {
        val displayHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()
        bottomSheetBehavior.apply {
            halfExpandedRatio = (120.dpToPx(resources).toFloat() / displayHeight)
            peekHeight = 120.dpToPx(resources)
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
            isHideable = true
        }

        val data = getRandomDummyTest()
        binding.apply {
            tvDesc.text = data.description
            qualitySni.qualityScore = data.sni
            qualityScaa.qualityScore = data.scaa
            qualitySni.text = data.sniDesc
            qualityScaa.text = data.scaaDesc
            tvDetail.text = data.detail
        }

        binding.btnSave.apply {
            setOnClickListener {
                lifecycleScope.launch {
                    viewModel.addTestResult(
                        TestResultEntity(
                            testId = 0,
                            testTimeStamp = getTimeStamp(),
                            testImage = imageUri.toString(),
                            testDescription = data.description,
                            testSniScore = data.sni,
                            testScaaScore = data.scaa,
                            testDetail = data.detail
                        )
                    )
                    isEnabled = false
                    customToast.showToast(getString(R.string.save_success))
                }
            }
        }
    }

    private fun calculateCropRect(
        bitmap: Bitmap,
        cameraFrame: View
    ): Rect {
        val scaleX = bitmap.width.toFloat() / cameraFrame.width
        val scaleY = bitmap.height.toFloat() / cameraFrame.height

        val scaledWidth = (cameraFrame.width * scaleX).toInt()
        val scaledHeight = (cameraFrame.height * scaleY).toInt()

        val size = minOf(scaledWidth, scaledHeight)
        val left = (bitmap.width - size) / 2
        val top = (bitmap.height - size) / 2

        return Rect(left, top, left + size, top + size)
    }

    private fun removeCameraFocusFrame() {
        if (cameraFocusFrame != null) {
            binding.root.removeView(cameraFocusFrame)
            cameraFocusFrame = null
        }
    }
    private fun deleteImage(uri: Uri) {
        try {
            val contentResolver: ContentResolver = contentResolver

            contentResolver.delete(uri, null, null)
        } catch (e: Exception) {
            customToast.showToast(getString(R.string.error_message))
        }
    }


    private fun allPermissionsGranted(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                this@ScanActivity, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(
            REQUIRED_PERMISSIONS
        )
    }

    override fun onResume() {
        super.onResume()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }
    }

    override fun onPause() {
        super.onPause()

        clearTempImages(this)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onDestroy() {
        super.onDestroy()

        clearTempImages(this)
        _binding = null
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}