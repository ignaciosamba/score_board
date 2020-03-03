package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class Detail(
    val athletesInvolved: List<AthletesInvolved>,
    val clock: Clock,
    val ownGoal: Boolean,
    val penaltyKick: Boolean,
    val redCard: Boolean,
    val scoreValue: Int,
    val scoringPlay: Boolean,
    val shootout: Boolean,
    val team: TeamXX,
    val type: Type,
    val yellowCard: Boolean
)