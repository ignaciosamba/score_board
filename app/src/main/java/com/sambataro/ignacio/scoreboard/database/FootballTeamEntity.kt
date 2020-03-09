package com.sambataro.ignacio.scoreboard.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sambataro.ignacio.scoreboard.domain.FootballTeamInfo
import com.sambataro.ignacio.scoreboard.domain.NBATeamInfo

@Entity(tableName = "football_teams_table")
class FootballTeamEntity (
    @PrimaryKey
    val id: String,
    val position: String,
    val name: String,
    val logo: String,
    val win: String,
    val lose: String,
    val gamesPlayed : String,
    val points : String,
    val leagueName: String)

/**
 * Map DatabaseVideos to domain entities
 */
fun List<FootballTeamEntity>.asFootballDomainModel(): List<FootballTeamInfo> {
    return map {
        FootballTeamInfo(
            id = it.id,
            position = it.position.substringBefore(".").toInt(),
            name = it.name,
            logo = it.logo,
            win = it.win,
            lose = it.lose,
            gamesPlayed = it.gamesPlayed,
            points = it.points,
            leagueName = it.leagueName)
    }
}