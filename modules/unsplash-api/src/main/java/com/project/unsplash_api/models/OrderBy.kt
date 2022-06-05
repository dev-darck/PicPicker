package com.project.unsplash_api.models

enum class OrderBy(val jsonName: String) {
    Latest("latest"),
    Oldest("oldest"),
    Popular("popular");
}