package com.project.model

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slug") val slug: String? = null,
    @SerializedName("pretty_slug") val prettySlug: String? = null,
)