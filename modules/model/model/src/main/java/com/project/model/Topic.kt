package com.project.model


import com.google.gson.annotations.SerializedName

data class Topic(
    @SerializedName("id")
    val id: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("visibility")
    val visibility: String
)