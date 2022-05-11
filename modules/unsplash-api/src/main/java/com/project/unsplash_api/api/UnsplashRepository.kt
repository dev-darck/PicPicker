package com.project.unsplash_api.api

interface UnsplashRepository {
    suspend fun <T> user(): T
}

