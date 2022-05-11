package com.project.unsplash_api.api

class UnsplashRepositoryImpl(
    private val unsplashApi: UnsplashApi,
) : UnsplashRepository {

    override suspend fun <T> user(): T =
        unsplashApi.currentUser()
}