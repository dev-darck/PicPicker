package com.project.model

import com.google.gson.annotations.SerializedName

data class Tags(
    @SerializedName("type") val type: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("source") val source: Source? = null,
)