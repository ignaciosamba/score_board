package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class Record(
    val abbreviation: String,
    val name: String,
    val summary: String,
    val type: String
)