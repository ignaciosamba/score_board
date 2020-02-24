package com.sambataro.ignacio.scoreboard.data.datanba


data class Sport(
    val id: String,
    val leagues: List<League>,
    val name: String,
    val slug: String,
    val uid: String
)