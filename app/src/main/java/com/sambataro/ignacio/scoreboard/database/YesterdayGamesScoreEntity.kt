package com.sambataro.ignacio.scoreboard.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sambataro.ignacio.scoreboard.domain.GameScoreInfo

@Entity(tableName = "yesterday_games_score_table")
class YesterdayGamesScoreEntity (
    @PrimaryKey
    val game_id: String,
    val home_name: String,
    val home_logo: String,
    val home_score: String,
    val away_name: String,
    val away_logo: String,
    val away_score : String,
    val game_date : String,
    val game_status : String)

fun List<YesterdayGamesScoreEntity>.asGameScoreDomainModel(): List<GameScoreInfo> {
    return map {
        GameScoreInfo(
            game_id = it.game_id,
            home_name = it.home_name,
            home_logo = it.home_logo,
            home_score = it.home_score,
            away_name = it.away_name,
            away_logo = it.away_logo,
            away_score = it.away_score,
            game_date = it.game_date,
            game_status = it.game_status
            )
    }
}

