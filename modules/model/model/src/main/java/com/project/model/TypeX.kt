package com.project.model


import com.google.gson.annotations.SerializedName

data class TypeX(
    @SerializedName("pretty_slug")
    val prettySlug: String,
    @SerializedName("slug")
    val slug: String
)