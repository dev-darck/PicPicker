package com.project.unsplash_api.api

import com.project.model.CollectionModel
import com.project.model.User
import com.project.unsplash_api.ResultWrapper

interface UnsplashRepository {
    suspend fun user(): ResultWrapper<User>
    suspend fun collections(page: String): ResultWrapper<List<CollectionModel>>
}