package com.sambataro.ignacio.scoreboard.data.datafootball


import com.google.gson.annotations.SerializedName

data class FootballTeamInfo(
    val abbreviation: String,
    val alternateColor: String,
    val color: String,
    val displayName: String,
    val id: String,
    val isActive: Boolean,
    val isAllStar: Boolean,
    val links: List<Link>,
    val location: String,
    val logos: List<Logo>,
    val name: String,
    val record: Record,
    val shortDisplayName: String,
    val slug: String,
    val uid: String
)