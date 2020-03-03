package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class Competition(
    val attendance: Int,
    val broadcasts: List<Any>,
    val competitors: List<Competitor>,
    val conferenceCompetition: Boolean,
    val date: String,
    val details: List<Detail>,
    val gamecastAvailable: Boolean,
    val geoBroadcasts: List<Any>,
    val id: String,
    val neutralSite: Boolean,
    val notes: List<Any>,
    val recent: Boolean,
    val situation: Situation,
    val startDate: String,
    val status: Status,
    val timeValid: Boolean,
    val uid: String,
    val venue: VenueX
)