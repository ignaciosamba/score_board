package com.sambataro.ignacio.scoreboard.data


import com.google.gson.annotations.SerializedName

data class  League(
    val abbreviation: String,
    val id: String,
    val name: String,
    val slug: String,
    val teams: List<Team>,
    val uid: String
)