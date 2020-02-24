package com.sambataro.ignacio.scoreboard.network

import com.sambataro.ignacio.scoreboard.data.datafootball.FootballTeamResponse
import com.sambataro.ignacio.scoreboard.data.datanba.NBATeamsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * A retrofit service to fetch a data.
 */
interface EndPoinService {
    @GET("basketball/{league}/teams")
    fun getTeamList(@Path("league") league: String): Deferred<NBATeamsResponse>

    @GET("soccer/{league}/teams")
    fun getFootballTeamList(@Path("league") league: String): Deferred<FootballTeamResponse>
}