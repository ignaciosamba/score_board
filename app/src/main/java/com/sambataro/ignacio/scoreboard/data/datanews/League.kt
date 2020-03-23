package com.sambataro.ignacio.scoreboard.data.datanews


import com.google.gson.annotations.SerializedName

data class League(
    val description: String,
    val id: Int,
    val links: Links
)