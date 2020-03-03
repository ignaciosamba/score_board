package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class Link(
    val href: String,
    val isExternal: Boolean,
    val isPremium: Boolean,
    val rel: List<String>,
    val text: String
)