package com.project.model

import com.google.gson.annotations.SerializedName

data class PhotoModel(
    @SerializedName("id") val id: String? = null,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("width") val width: Float = 0F,
    @SerializedName("height") val height: Float = 0F,
    @SerializedName("blur_hash") val blurHash: String? = null,
) {
    var aspectRatio = 0F
    var measureheight = 0
}
