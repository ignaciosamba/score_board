package com.sambataro.ignacio.scoreboard.data


import com.google.gson.annotations.SerializedName

data class Link(
    val href: String,
    val isExternal: Boolean,
    val isPremium: Boolean,
    val language: String,
    val rel: List<String>,
    val shortText: String,
    val text: String
)