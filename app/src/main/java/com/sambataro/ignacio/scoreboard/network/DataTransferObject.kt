package com.sambataro.ignacio.scoreboard.network

import com.sambataro.ignacio.scoreboard.data.League
import com.sambataro.ignacio.scoreboard.data.Team
import com.sambataro.ignacio.scoreboard.data.TeamsResponse
import com.sambataro.ignacio.scoreboard.database.TeamsEntity




/**
 * Convert Network results to database objects
 */
fun TeamsResponse.asDatabaseModel(): List<TeamsEntity> {
    var league = ArrayList<League>()
    var teams = ArrayList<Team>()
    sports.forEach {
        league = ArrayList(it.leagues)
    }
    league.forEach {
        teams = ArrayList(it.teams)
    }

    return teams.map {
        TeamsEntity(
            id = it.team.id,
            name = it.team.name,
            logo = it.team.logos[0].href,
            win = it.team.record.items[0].stats[1].value.toString(),
            lose = it.team.record.items[0].stats[2].value.toString(),
            winningPercentage = it.team.record.items[0].stats[3].value.toString(),
            gb = it.team.record.items[0].stats[4].value.toString())
    }
}