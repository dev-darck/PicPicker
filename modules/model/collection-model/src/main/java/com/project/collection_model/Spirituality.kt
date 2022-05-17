package com.project.collection_model

import com.google.gson.annotations.SerializedName

data class Spirituality(
	@SerializedName("status") val status: String? = null,
	@SerializedName("approved_on") val approvedOn: String? = null,
)