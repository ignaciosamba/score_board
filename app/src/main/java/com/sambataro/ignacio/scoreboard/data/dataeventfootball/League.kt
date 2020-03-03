package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class League(
    val abbreviation: String,
    val calendar: List<String>,
    val calendarEndDate: String,
    val calendarIsWhitelist: Boolean,
    val calendarStartDate: String,
    val calendarType: String,
    val id: String,
    val midsizeName: String,
    val name: String,
    val season: SeasonX,
    val slug: String,
    val uid: String
)