package com.project.model

import com.google.gson.annotations.SerializedName

data class TopicSubmissions(
    @SerializedName("wallpapers") val wallpapers: Wallpapers? = null,
    @SerializedName("experimental") val experimental: Experimental? = null,
    @SerializedName("spirituality") val spirituality: Spirituality? = null,
)
