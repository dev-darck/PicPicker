package com.project.model

import com.google.gson.annotations.SerializedName

data class CoverPhoto(
	@SerializedName("id") val id: String? = null,
	@SerializedName("created_at") val createdAt: String? = null,
	@SerializedName("updated_at") val updatedAt: String? = null,
	@SerializedName("promoted_at") val promotedAt: String? = null,
	@SerializedName("width") val width: Int? = null,
	@SerializedName("height") val height: Int? = null,
	@SerializedName("color") val color: String? = null,
	@SerializedName("blur_hash") val blurHash: String? = null,
	@SerializedName("description") val description: String? = null,
	@SerializedName("alt_description") val altDescription: String? = null,
	@SerializedName("urls") val urls: Urls? = null,
	@SerializedName("links") val links: Links? = null,
	@SerializedName("categories") val categories: List<String>? = null,
	@SerializedName("likes") val likes: Int? = null,
	@SerializedName("liked_by_user") val likedByUser: Boolean? = null,
	@SerializedName("current_user_collections") val currentUserCollections: List<String>? = null,
	@SerializedName("sponsorship") val sponsorship: String? = null,
	@SerializedName("topic_submissions") val topicSubmissions: TopicSubmissions? = null,
	@SerializedName("user") val user: User? = null,
)