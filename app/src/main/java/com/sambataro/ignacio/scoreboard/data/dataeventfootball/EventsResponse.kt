package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class EventsResponse(
    val events: List<Event>,
    val leagues: List<League>
)