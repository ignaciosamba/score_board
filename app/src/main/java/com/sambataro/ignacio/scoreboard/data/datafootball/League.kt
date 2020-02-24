package com.sambataro.ignacio.scoreboard.data.datafootball


data class League(
    val abbreviation: String,
    val id: String,
    val name: String,
    val shortName: String,
    val slug: String,
    val teams: List<FootballTeamList>,
    val uid: String
)