package com.sambataro.ignacio.scoreboard.data.datanba


data class Link(
    val href: String,
    val isExternal: Boolean,
    val isPremium: Boolean,
    val language: String,
    val rel: List<String>,
    val shortText: String,
    val text: String
)