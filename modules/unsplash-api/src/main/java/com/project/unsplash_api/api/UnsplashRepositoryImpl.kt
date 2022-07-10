package com.project.unsplash_api.api

import com.project.model.*
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.extensions.safeCall
import com.project.unsplash_api.models.OrderBy

class UnsplashRepositoryImpl(
    private val unsplashApi: UnsplashApi,
) : UnsplashRepository {

    override suspend fun user(): ResultWrapper<User> =
        unsplashApi.currentUser().safeCall()

    override suspend fun collections(
        page: Int,
        maxPage: Int
    ): ResultWrapper<List<CollectionModel>> =
        unsplashApi.collections(page.toString(), maxPage.toString()).safeCall()

    override suspend fun collectionsById(
        id: String,
        page: Int,
        maxPage: Int
    ): ResultWrapper<List<PhotoModel>> =
        unsplashApi.collectionsById(id, page.toString(), maxPage.toString()).safeCall()

    override suspend fun photos(page: Int, orderBy: OrderBy): ResultWrapper<List<PhotoModel>> =
        unsplashApi.photos(page = page.toString(), orderBy = orderBy.jsonName).safeCall()

    override suspend fun photo(id: String): ResultWrapper<Photo> =
        unsplashApi.photo(id).safeCall()

    override suspend fun searchPhoto(query: String, page: Int, maxPage: Int): ResultWrapper<SearchPhoto> =
        unsplashApi.searchPhoto(page.toString(), query, maxPage.toString()).safeCall()

    override suspend fun searchCollection(query: String, page: Int, maxPage: Int): ResultWrapper<SearchCollection> =
        unsplashApi.searchCollection(page.toString(), query, maxPage.toString()).safeCall()

    override suspend fun searchUser(query: String, page: Int, maxPage: Int): ResultWrapper<SearchUser> =
        unsplashApi.searchUser(page.toString(), query, maxPage.toString()).safeCall()
}