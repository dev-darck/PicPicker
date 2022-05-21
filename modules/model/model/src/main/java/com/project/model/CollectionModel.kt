package com.project.model

import com.google.gson.annotations.SerializedName

data class CollectionModel(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("published_at") val publishedAt: String? = null,
    @SerializedName("last_collected_at") val lastCollectedAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("curated") val curated: Boolean? = null,
    @SerializedName("featured") val featured: Boolean? = null,
    @SerializedName("total_photos") val totalPhotos: Int? = null,
    @SerializedName("private") val private: Boolean? = null,
    @SerializedName("share_key") val shareShareKey: String? = null,
    @SerializedName("tags") val tags: List<Tags>? = null,
    @SerializedName("links") val links: Links? = null,
    @SerializedName("user") val user: User? = null,
    @SerializedName("cover_photo") val coverPhoto: CoverPhoto? = null,
    @SerializedName("preview_photos") val previewPhotos: List<PreviewPhotos>? = null,
)