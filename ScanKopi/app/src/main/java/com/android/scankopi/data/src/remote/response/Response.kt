package com.android.scankopi.data.src.remote.response

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("image")
	val image: Image? = null,

	@field:SerializedName("inference_id")
	val inferenceId: String? = null,

	@field:SerializedName("time")
	val time: Any? = null,

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItem>? = null
)

data class PredictionsItem(

	@field:SerializedName("confidence")
	val confidence: Float,

	@field:SerializedName("class_id")
	val classId: Int? = null,

	@field:SerializedName("x")
	val x: Float,

	@field:SerializedName("width")
	val width: Float,

	@field:SerializedName("y")
	val y: Float,

	@field:SerializedName("detection_id")
	val detectionId: String? = null,

	@field:SerializedName("class")
	val jsonMemberClass: String? = null,

	@field:SerializedName("height")
	val height: Float
)

data class Image(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
