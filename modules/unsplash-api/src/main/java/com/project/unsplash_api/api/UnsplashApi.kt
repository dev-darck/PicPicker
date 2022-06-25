package com.project.unsplash_api.api

import androidx.annotation.CheckResult
import com.project.model.CollectionModel
import com.project.model.Photo
import com.project.model.PhotoModel
import com.project.model.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UnsplashApi {
    @GET("me")
    suspend fun currentUser(): Response<User>

    @PUT("me")
    suspend fun updateUser(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("collections")
    @CheckResult
    suspend fun collections(
        @Query("page") page: String,
        @Query("per_page") perPage: String = "30",
    ): Response<List<CollectionModel>>

    @GET("/collections/{id}/photos")
    suspend fun collectionsById(
        @Path("id") id: String,
        @Query("page") page: String,
        @Query("per_page") perPage: String = "30",
    ): Response<List<PhotoModel>>

    @GET("photos")
    @CheckResult
    suspend fun photos(
        @Query("page") page: String,
        @Query("per_page") perPage: String = "30",
        @Query("order_by") orderBy: String,
    ): Response<List<PhotoModel>>

    @GET("photos/{id}")
    suspend fun photo(
        @Path("id") id: String,
    ): Response<Photo>
}