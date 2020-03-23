package com.sambataro.ignacio.scoreboard.data.datanews


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("api")
    val api: ApiX,
    @SerializedName("mobile")
    val mobile: MobileX,
    @SerializedName("web")
    val web: WebX
)