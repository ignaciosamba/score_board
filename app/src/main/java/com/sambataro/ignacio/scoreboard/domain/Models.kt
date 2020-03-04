package com.sambataro.ignacio.scoreboard.domain



abstract class SportTeamInfo(
    val sportType: String,
    val team_id: String,
    val team_playoffseed: Int,
    val team_name: String,
    val team_logo: String,
    val team_win: String,
    val team_lose: String) {
    class SPORTTYPE {
        companion object {
            val NBA = "nba"
            val FOOTBALL = "football"
        }
    }
}

data class NBATeamInfo(
    val id: String,
    val playoffseed: Int,
    val name: String,
    val logo: String,
    val win: String,
    val lose: String,
    val winningPercentage: String,
    val gb: String) : SportTeamInfo(SPORTTYPE.NBA, id, playoffseed, name, logo, win, lose)

data class FootballTeamInfo(
    val id: String,
    val position: Int,
    val name: String,
    val logo: String,
    val win: String,
    val lose: String,
    val gamesPlayed: String,
    val points: String) : SportTeamInfo(SPORTTYPE.FOOTBALL, id, position, name, logo, win, lose)

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