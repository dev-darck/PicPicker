package com.project.collection_model

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("ancestry") val ancestry: Ancestry? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("meta_title") val metaTitle: String? = null,
    @SerializedName("meta_description") val metaDescription: String? = null,
    @SerializedName("cover_photo") val coverPhoto: CoverPhoto? = null,
)