package com.sambataro.ignacio.scoreboard.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val NBA_BASE = "https://site.api.espn.com/apis/site/v2/sports/"


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

    val response = retrofit.create(EndPoinService::class.java)
}