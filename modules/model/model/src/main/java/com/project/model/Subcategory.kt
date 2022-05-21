package com.project.model

import com.google.gson.annotations.SerializedName

data class Subcategory(
    @SerializedName("slug") val slug: String,
    @SerializedName("pretty_slug") val pretty_slug: String,
)