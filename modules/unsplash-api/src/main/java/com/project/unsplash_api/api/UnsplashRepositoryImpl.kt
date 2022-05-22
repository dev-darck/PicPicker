package com.project.unsplash_api.api

import com.project.model.CollectionModel
import com.project.model.User
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.extensions.safeCall

class UnsplashRepositoryImpl(
    private val unsplashApi: UnsplashApi,
) : UnsplashRepository {

    override suspend fun user(): ResultWrapper<User> =
        unsplashApi.currentUser().safeCall()

    override suspend fun collections(
        page: Int,
        maxPage: Int,
    ): ResultWrapper<List<CollectionModel>> = unsplashApi.collections(page.toString(), maxPage.toString()).safeCall()
}