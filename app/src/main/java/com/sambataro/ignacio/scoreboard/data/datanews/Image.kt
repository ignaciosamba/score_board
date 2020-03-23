package com.sambataro.ignacio.scoreboard.data.datanews


import com.google.gson.annotations.SerializedName

data class Image(
    val caption: String,
    val credit: String,
    val height: Int,
    val id: Int,
    val name: String,
    val type: String,
    val url: String,
    val width: Int
)