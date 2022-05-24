package com.project.model

import com.google.gson.annotations.SerializedName

data class PhotoModel(
    @SerializedName("id") val id: String? = null,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("blur_hash") val blurHash: String? = null,
)
