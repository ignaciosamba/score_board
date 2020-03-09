package com.sambataro.ignacio.scoreboard.utils

object LeaguesIds {
    val SUPER_LIGA_ARGENTINA = "745"
    val PRIMER_B_NACIONAL = "3903"
}

fun getNameLeague(leagueId: String) : String {
    return when(leagueId){
        LeaguesIds.SUPER_LIGA_ARGENTINA -> "SuperLiga Argentina"
        LeaguesIds.PRIMER_B_NACIONAL -> "Primera B Nacional"
        else -> ""
    }
}
