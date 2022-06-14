package com.project.model


import com.google.gson.annotations.SerializedName

data class Covid19(
    @SerializedName("approved_on")
    val approvedOn: String,
    @SerializedName("status")
    val status: String
)