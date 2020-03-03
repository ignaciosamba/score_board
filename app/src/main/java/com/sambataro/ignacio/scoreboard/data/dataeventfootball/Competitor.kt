package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class Competitor(
    val form: String,
    val homeAway: String,
    val id: String,
    val order: Int,
    val records: List<Record>,
    val score: String,
    val statistics: List<Statistic>,
    val team: Team,
    val type: String,
    val uid: String,
    val winner: Boolean
)