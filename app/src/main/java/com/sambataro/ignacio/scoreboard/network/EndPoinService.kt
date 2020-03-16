package com.sambataro.ignacio.scoreboard.network

import com.sambataro.ignacio.scoreboard.data.dataeventfootball.EventsResponse
import com.sambataro.ignacio.scoreboard.data.datafootball.FootballTeamResponse
import com.sambataro.ignacio.scoreboard.data.datanba.NBATeamsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


/**
 * A retrofit service to fetch a data.
 */
interface EndPoinService {
    @GET("basketball/{league}/teams")
    fun getTeamList(@Path("league") league: String): Deferred<NBATeamsResponse>

    @GET("soccer/{league}/teams")
    fun getFootballTeamList(@Path("league") league: String): Deferred<FootballTeamResponse>

    @GET("{sport}/{league}/scoreboard")
    fun getBasketballGamesForYesterday(@Path("sport") sport: String,
                                       @Path("league") league: String,
                             @Query("dates") date: String) : Deferred<EventsResponse>

    @GET("soccer/{league}/scoreboard")
    fun getFootballGamesForYesterday(@Path("league") league: String,
                             @Query("dates") date: String) : Deferred<EventsResponse>
}

//basketball

//soccer