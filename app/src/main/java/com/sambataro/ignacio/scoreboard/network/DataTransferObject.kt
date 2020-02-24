package com.sambataro.ignacio.scoreboard.network

import com.sambataro.ignacio.scoreboard.data.datafootball.FootballTeamList
import com.sambataro.ignacio.scoreboard.data.datafootball.FootballTeamResponse
import com.sambataro.ignacio.scoreboard.data.datanba.League
import com.sambataro.ignacio.scoreboard.data.datanba.Team
import com.sambataro.ignacio.scoreboard.data.datanba.NBATeamsResponse
import com.sambataro.ignacio.scoreboard.database.FootballTeamEntity
import com.sambataro.ignacio.scoreboard.database.TeamsEntity


val NBA_TEAM_PLAYOFF_SEED = 0
val NBA_TEAM_WINS = 1
val NBA_TEAM_LOSSES = 2
val NBA_TEAM_WINNING_PERCENTAGE = 3
val NBA_TEAM_GB = 4

val FOOTBALL_TEAM_WIN = 0
val FOOTBALL_TEAM_LOSSES = 1
val FOOTBALL_TEAM_GAMES_PLAYED = 3
val FOOTBALL_TEAM_POSITION = 8

val FOOTBALL_TEAM_POINTS = 6

/**
 * Convert NBA Network results to database objects
 */
fun NBATeamsResponse.asDatabaseModel(): List<TeamsEntity> {
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
            playoffseed = it.team.record.items[0].stats[NBA_TEAM_PLAYOFF_SEED].value.toString(),
            name = it.team.name,
            logo = it.team.logos[0].href,
            win = it.team.record.items[0].stats[NBA_TEAM_WINS].value.toString(),
            lose = it.team.record.items[0].stats[NBA_TEAM_LOSSES].value.toString(),
            winningPercentage = it.team.record.items[0].stats[NBA_TEAM_WINNING_PERCENTAGE].value.toString(),
            gb = it.team.record.items[0].stats[NBA_TEAM_GB].value.toString())
    }
}


/**
 * Convert NBA Network results to database objects
 */
fun FootballTeamResponse.asDatabaseModel(): List<FootballTeamEntity> {
    var league = ArrayList<com.sambataro.ignacio.scoreboard.data.datafootball.League>()
    var teams = ArrayList<FootballTeamList>()
    sports.forEach {
        league = ArrayList(it.leagues)
    }
    league.forEach {
        teams = ArrayList(it.teams)
    }

    return teams.map {
        FootballTeamEntity(
            id = it.team.id,
            position = it.team.record.items[0].stats[FOOTBALL_TEAM_POSITION].value.toString(),
            name = it.team.name,
            logo = it.team.logos[0].href,
            win = it.team.record.items[0].stats[FOOTBALL_TEAM_WIN].value.toString(),
            lose = it.team.record.items[0].stats[FOOTBALL_TEAM_LOSSES].value.toString(),
            gamesPlayed = it.team.record.items[0].stats[FOOTBALL_TEAM_GAMES_PLAYED].value.toString(),
            points = it.team.record.items[0].stats[FOOTBALL_TEAM_POINTS].value.toString())
    }
}