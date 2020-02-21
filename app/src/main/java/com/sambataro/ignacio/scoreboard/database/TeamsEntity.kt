package com.sambataro.ignacio.scoreboard.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sambataro.ignacio.scoreboard.domain.TeamInfo

@Entity(tableName = "teams_table")
class TeamsEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val logo: String,
    val win: String,
    val lose: String,
    val winningPercentage : String,
    val gb : String)

/**
 * Map DatabaseVideos to domain entities
 */
fun List<TeamsEntity>.asDomainModel(): List<TeamInfo> {
    return map {
        TeamInfo(
            id = it.id,
            name = it.name,
            logo = it.logo,
            win = it.win,
            lose = it.lose,
            winningPercentage = it.winningPercentage,
            gb = it.gb)
    }
}