package com.project.model

import com.google.gson.annotations.SerializedName

data class SearchPhoto(
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val result: List<PhotoModel>?
) : SearchModel {
    override fun isEmpty(): Boolean {
        return result.isNullOrEmpty()
    }
}