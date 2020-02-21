package com.sambataro.ignacio.scoreboard.data


import com.google.gson.annotations.SerializedName

data class Logo(
    val alt: String,
    val height: Int,
    val href: String,
    val rel: List<String>,
    val width: Int
)