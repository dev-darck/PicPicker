package com.project.unsplash_api.api

import com.project.unsplash_api.ResultWrapper

interface UnsplashRepository {
    suspend fun <T: Any> user(classType: Class<T>): ResultWrapper<T>
    suspend fun <T: Any> collections(page: String, classType: Class<T>): ResultWrapper<List<T>>
}