package com.project.collection_model

import com.google.gson.annotations.SerializedName

data class Category(
	@SerializedName("slug") val slug: String,
	@SerializedName("prettySlug") val prettySlug: String,
)