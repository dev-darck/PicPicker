package com.project.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("position")
    val position: Position
)

fun Location?.name(): String? = this?.let {
    when {
        !city.isNullOrEmpty() && !country.isNullOrEmpty() -> "$country, $city"
        !city.isNullOrEmpty() -> city
        !country.isNullOrEmpty() -> country
        else -> null
    }
}