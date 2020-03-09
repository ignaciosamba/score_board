package com.sambataro.ignacio.scoreboard.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sambataro.ignacio.scoreboard.domain.NBATeamInfo

@Entity(tableName = "nba_teams_table")
class TeamsEntity (
    @PrimaryKey
    val id: String,
    val playoffseed: String,
    val name: String,
    val logo: String,
    val win: String,
    val lose: String,
    val winningPercentage : String,
    val gb : String,
    val leagueName: String)

/**
 * Map DatabaseVideos to domain entities
 */
fun List<TeamsEntity>.asNBADomainModel(): List<NBATeamInfo> {
    return map {
        NBATeamInfo(
            id = it.id,
            playoffseed = it.playoffseed.substringBefore(".").toInt(),
            name = it.name,
            logo = it.logo,
            win = it.win,
            lose = it.lose,
            winningPercentage = it.winningPercentage,
            gb = it.gb,
            leagueName =  it.leagueName)
    }
}