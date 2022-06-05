package com.project.unsplash_api.api

import androidx.annotation.IntRange
import com.project.model.CollectionModel
import com.project.model.PhotoModel
import com.project.model.User
import com.project.unsplash_api.ResultWrapper

interface UnsplashRepository {

    suspend fun user(): ResultWrapper<User>

    suspend fun collections(
        page: Int,
        @IntRange(from = 1, to = 30) maxPage: Int,
    ): ResultWrapper<List<CollectionModel>>

    suspend fun new(page: Int): ResultWrapper<List<PhotoModel>>
}