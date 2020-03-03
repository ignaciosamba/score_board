package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class StatusX(
    val clock: Double,
    val displayClock: String,
    val period: Int,
    val type: TypeXX
)