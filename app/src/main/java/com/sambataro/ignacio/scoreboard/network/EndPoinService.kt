package com.sambataro.ignacio.scoreboard.network

import com.sambataro.ignacio.scoreboard.data.TeamsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET


/**
 * A retrofit service to fetch a data.
 */
interface EndPoinService {
    @GET("teams")
    fun getTeamList(): Deferred<TeamsResponse>
}