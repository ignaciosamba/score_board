package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class AthletesInvolved(
    val displayName: String,
    val fullName: String,
    val id: String,
    val jersey: String,
    val links: List<LinkX>,
    val position: String,
    val shortName: String,
    val team: TeamX
)