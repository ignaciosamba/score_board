package com.sambataro.ignacio.scoreboard.network

import android.util.Log
import com.sambataro.ignacio.scoreboard.data.dataeventfootball.Event
import com.sambataro.ignacio.scoreboard.data.dataeventfootball.EventsResponse
import com.sambataro.ignacio.scoreboard.data.datafootball.FootballTeamList
import com.sambataro.ignacio.scoreboard.data.datafootball.FootballTeamResponse
import com.sambataro.ignacio.scoreboard.data.datanba.League
import com.sambataro.ignacio.scoreboard.data.datanba.Team
import com.sambataro.ignacio.scoreboard.data.datanba.NBATeamsResponse
import com.sambataro.ignacio.scoreboard.database.FootballTeamEntity
import com.sambataro.ignacio.scoreboard.database.TeamsEntity
import com.sambataro.ignacio.scoreboard.database.YesterdayGamesScoreEntity
import com.sambataro.ignacio.scoreboard.domain.GameScoreInfo


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

fun NBATeamsResponse.getBasketBallLeagueName(): String {
    var league = ArrayList<League>()
    sports.forEach {
        league = ArrayList(it.leagues)
    }
    return league[0].name
}

fun FootballTeamResponse.getFootBallLeagueName(): String {
    var league = ArrayList<com.sambataro.ignacio.scoreboard.data.datafootball.League>()
    sports.forEach {
        league = ArrayList(it.leagues)
    }
    return league[0].name
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


fun EventsResponse.asDataBaseModel(): List<YesterdayGamesScoreEntity> {
    return events.map {
        YesterdayGamesScoreEntity(
            game_id = it.id,
            home_name = it.competitions[0].competitors[0].team.displayName,
            home_logo = it.competitions[0].competitors[0].team.logo,
            home_score = it.competitions[0].competitors[0].score,
            away_name = it.competitions[0].competitors[1].team.displayName,
            away_logo = it.competitions[0].competitors[1].team.logo,
            away_score = it.competitions[0].competitors[1].score,
            game_status = it.status.type.detail,
            game_date = it.date.substringBefore("T")
            )
    }
}