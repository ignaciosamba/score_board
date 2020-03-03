package com.sambataro.ignacio.scoreboard.data.dataeventfootball


import com.google.gson.annotations.SerializedName

data class VenueX(
    val address: Address,
    val capacity: Int,
    val fullName: String,
    val id: String
)