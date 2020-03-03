package com.sambataro.ignacio.scoreboard.domain

import androidx.room.PrimaryKey


data class NBATeamInfo(
    val id: String,
    val playoffseed: Int,
    val name: String,
    val logo: String,
    val win: String,
    val lose: String,
    val winningPercentage : String,
    val gb : String)

data class FootballTeamInfo(
    val id: String,
    val position: Int,
    val name: String,
    val logo: String,
    val win: String,
    val lose: String,
    val gamesPlayed : String,
    val points : String)

data class GameScoreInfo(
    val game_id: String,
    val home_name: String,
    val home_logo: String,
    val home_score: String,
    val away_name: String,
    val away_logo: String,
    val away_score : String,
    val game_date : String,
    val game_status : String)