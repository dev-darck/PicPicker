package com.project.unsplash_api.api

import com.project.unsplash_api.result
import com.project.unsplash_api.resultLitOf

class UnsplashRepositoryImpl(
    private val unsplashApi: UnsplashApi,
) : UnsplashRepository {

    override suspend fun <T : Any> user(classType: Class<T>): T =
        unsplashApi.currentUser().result(classType)

    override suspend fun <T : Any> collections(page: String, classType: Class<T>): List<T> =
        unsplashApi.collections(page).resultLitOf(classType)
}