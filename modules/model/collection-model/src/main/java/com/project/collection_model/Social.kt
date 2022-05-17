package com.project.collection_model

import com.google.gson.annotations.SerializedName

data class Social(
    @SerializedName("instagram_username") val instagram_username: String? = null,
    @SerializedName("portfolio_url") val portfolioUrl: String? = null,
    @SerializedName("twitter_username") val twitterUsername: String? = null,
    @SerializedName("paypal_email") val paypalEmail: String? = null,
)