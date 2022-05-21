package com.project.unsplash_api.api

import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.safeCall
import com.project.unsplash_api.safeListCall

class UnsplashRepositoryImpl(
    private val unsplashApi: UnsplashApi,
) : UnsplashRepository {

    override suspend fun <T : Any> user(classType: Class<T>): ResultWrapper<T> =
        unsplashApi.currentUser().safeCall(classType)

    override suspend fun <T : Any> collections(
        page: String,
        classType: Class<T>,
    ): ResultWrapper<List<T>> = unsplashApi.collections(page).safeListCall(classType)
}