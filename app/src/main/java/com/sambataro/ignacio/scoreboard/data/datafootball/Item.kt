package com.sambataro.ignacio.scoreboard.data.datafootball


import com.google.gson.annotations.SerializedName

data class Item(
    val stats: List<Stat>,
    val summary: String
)