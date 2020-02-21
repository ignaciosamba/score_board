package com.sambataro.ignacio.scoreboard.domain

import androidx.room.PrimaryKey


data class TeamInfo(
    val id: String,
    val name: String,
    val logo: String,
    val win: String,
    val lose: String,
    val winningPercentage : String,
    val gb : String)