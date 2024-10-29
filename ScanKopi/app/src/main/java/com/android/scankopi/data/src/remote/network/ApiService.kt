package com.android.scankopi.data.src.remote.network

import com.android.scankopi.data.src.remote.response.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("consolidated/1")
    suspend fun postScanResult (
        @Query("api_key") key: String,
        @Query("image") image: String
    ): retrofit2.Response<Response>
}