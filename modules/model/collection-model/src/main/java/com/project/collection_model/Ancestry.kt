package com.project.collection_model

import com.google.gson.annotations.SerializedName

data class Ancestry(
	@SerializedName("type") val type: Type? = null,
	@SerializedName("category") val category: Category? = null,
	@SerializedName("subcategory") val subcategory: Subcategory? = null,
)