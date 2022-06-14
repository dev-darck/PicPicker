package com.project.model


import com.google.gson.annotations.SerializedName

data class Work(
    @SerializedName("approved_on")
    val approvedOn: String,
    @SerializedName("status")
    val status: String
)