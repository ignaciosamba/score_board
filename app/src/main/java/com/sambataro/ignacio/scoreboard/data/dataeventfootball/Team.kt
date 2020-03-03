package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class Team(
    val abbreviation: String,
    val alternateColor: String,
    val color: String,
    val displayName: String,
    val id: String,
    val isActive: Boolean,
    val links: List<Link>,
    val location: String,
    val logo: String,
    val name: String,
    val shortDisplayName: String,
    val uid: String,
    val venue: Venue
)