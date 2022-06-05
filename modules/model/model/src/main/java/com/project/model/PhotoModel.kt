package com.project.model

import com.google.gson.annotations.SerializedName

data class PhotoModel(
    @SerializedName("blur_hash")
    val blurHash: String? = null,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<CurrentUserCollection> = emptyList(),
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("height")
    val height: Float = 0F,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean? = null,
    @SerializedName("likes")
    val likes: Int? = null,
    @SerializedName("links")
    val links: Links? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("urls")
    val urls: Urls? = null,
    @SerializedName("user")
    val user: User = User(),
    @SerializedName("width")
    val width: Float = 0F,
    var aspectRatio: Float = 0F,
    var measureHeight: Int = 0
)
