package com.project.model


import com.google.gson.annotations.SerializedName

data class PositionX(
    @SerializedName("latitude")
    val latitude: Any,
    @SerializedName("longitude")
    val longitude: Any
)