package com.sambataro.ignacio.scoreboard.data.datanews


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    val header: String,
    val link: Link
)