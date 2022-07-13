package com.project.unsplash_api.api

import androidx.annotation.IntRange
import com.project.model.*
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.models.OrderBy

interface UnsplashRepository {
    suspend fun user(): ResultWrapper<User>
    suspend fun collections(
        page: Int,
        @IntRange(from = 1, to = 30) maxPage: Int,
    ): ResultWrapper<List<CollectionModel>>

    suspend fun collectionsById(
        id: String,
        page: Int,
        @IntRange(from = 1, to = 30) maxPage: Int
    ): ResultWrapper<List<PhotoModel>>

    suspend fun photos(page: Int, orderBy: OrderBy): ResultWrapper<List<PhotoModel>>
    suspend fun photo(id: String): ResultWrapper<Photo>
    suspend fun searchPhoto(query: String, page: Int, maxPage: Int): ResultWrapper<SearchPhoto>
    suspend fun searchCollection(query: String, page: Int, maxPage: Int): ResultWrapper<SearchCollection>
    suspend fun searchUser(query: String, page: Int, maxPage: Int): ResultWrapper<SearchUser>
}