package com.sambataro.ignacio.scoreboard.data.datafootball


import com.google.gson.annotations.SerializedName

data class Sport(
    val id: String,
    val leagues: List<League>,
    val name: String,
    val slug: String,
    val uid: String
)