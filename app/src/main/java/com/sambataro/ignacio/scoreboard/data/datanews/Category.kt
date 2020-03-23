package com.sambataro.ignacio.scoreboard.data.datanews


import com.google.gson.annotations.SerializedName

data class Category(
    val createDate: String,
    val description: String,
    val guid: String,
    val id: Int,
    val league: League,
    val leagueId: Int,
    val sportId: Int,
    val topicId: Int,
    val type: String,
    val uid: String
)