package com.project.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("twitter_username") val twitterUsername: String? = null,
    @SerializedName("portfolio_url") val portfolioUrl: String? = null,
    @SerializedName("bio") val bio: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("links") val links: Links? = null,
    @SerializedName("profile_image") val profileImage: ProfileImage? = null,
    @SerializedName("instagram_username") val instagramUsername: String? = null,
    @SerializedName("total_collections") val totalCollections: Int? = null,
    @SerializedName("total_likes") val totalLikes: Int? = null,
    @SerializedName("total_photos") val totalPhotos: Int? = null,
    @SerializedName("accepted_tos") val acceptedTos: Boolean? = null,
    @SerializedName("for_hire") val forHire: Boolean? = null,
    @SerializedName("social") val social: Social? = null,
)

fun User?.name(prefix: String): String {
    return this?.let {
        val userName = when {
            name?.isNotEmpty() == true -> name
            firstName?.isNotEmpty() == true && this.lastName?.isNotEmpty() == true -> "$firstName $lastName"
            username?.isNotEmpty() == true -> username
            instagramUsername?.isNotEmpty() == true -> instagramUsername
            else -> ""
        }
        if (userName.isNotEmpty()) "$prefix $userName" else userName
    }.orEmpty()
}