package com.project.unsplash_api.api

import androidx.annotation.CheckResult
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface UnsplashApi {
    @GET("me")
    suspend fun currentUser(): Response<ResponseBody>

    @PUT("me")
    suspend fun updateUser(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("collections")
    @CheckResult
    suspend fun collections(
        @Query("page") page: String,
        @Query("per_page") perPage: String = "30",
    ): Response<ResponseBody>
}