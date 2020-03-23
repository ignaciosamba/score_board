package com.sambataro.ignacio.scoreboard.data.datanews


import com.google.gson.annotations.SerializedName

data class Article(
    val categories: List<Category>,
    @SerializedName("description")
    val description: String,
    @SerializedName("headline")
    val headline: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("links")
    val links: LinksX
)