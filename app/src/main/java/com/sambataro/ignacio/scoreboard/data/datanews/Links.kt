package com.sambataro.ignacio.scoreboard.data.datanews


import com.google.gson.annotations.SerializedName

data class Links(
    val api: Api,
    val mobile: Mobile,
    val web: Web
)