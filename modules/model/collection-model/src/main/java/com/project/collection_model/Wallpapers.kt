package com.project.collection_model

import com.google.gson.annotations.SerializedName

data class Wallpapers(
    @SerializedName("status") val status: String? = null,
    @SerializedName("approved_on") val approvedOn: String? = null,
)