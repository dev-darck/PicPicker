package com.project.unsplash_api.api

interface UnsplashRepository {
    suspend fun <T: Any> user(classType: Class<T>): T
    suspend fun <T: Any> collections(page: String, classType: Class<T>): List<T>
}