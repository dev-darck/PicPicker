package com.project.model


import com.google.gson.annotations.SerializedName

data class Photo(
    val altDescription: Any,
    @SerializedName("blur_hash")
    val blurHash: String,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("color")
    val color: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any>,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("exif")
    val exif: Exif,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("links")
    val links: Links,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("promoted_at")
    val promotedAt: String? = null,
    @SerializedName("public_domain")
    val publicDomain: Boolean,
    @SerializedName("related_collections")
    val relatedCollections: RelatedCollections,
    @SerializedName("sponsorship")
    val sponsorship: Sponsorship,
    @SerializedName("tags")
    val tags: List<Tags>? = emptyList(),
    @SerializedName("tags_preview")
    val tagsPreview: List<Tags>,
    @SerializedName("topic_submissions")
    val topicSubmissions: TopicSubmissions,
    @SerializedName("topics")
    val topics: List<Topic>,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("user")
    val user: User,
    @SerializedName("views")
    val views: Int,
    @SerializedName("width")
    val width: Int
)

fun Photo.relatedCollections(): List<Result> = relatedCollections.results ?: emptyList()