package com.project.unsplash_api.api

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UnsplashApi {
    @GET("me")
    suspend fun <T> currentUser(): T

    @PUT("me")
    suspend fun <T> updateUser(@Body requestBody: RequestBody): T
}