package com.sambataro.ignacio.scoreboard.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sambataro.ignacio.scoreboard.data.TeamsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


val NBA_BASE = "http://site.api.espn.com/apis/site/v2/sports/basketball/nba/"


/**
 * Main entry point for network access. Call like `EndPoinService.getTeamList()`
 */
object TeamsNetwork {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(NBA_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val teamsResponse = retrofit.create(EndPoinService::class.java)
}