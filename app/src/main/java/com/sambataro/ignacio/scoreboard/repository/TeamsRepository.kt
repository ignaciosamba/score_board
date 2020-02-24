package com.sambataro.ignacio.scoreboard.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sambataro.ignacio.scoreboard.database.ScoreBoardDataBase
import com.sambataro.ignacio.scoreboard.database.asFootballDomainModel
import com.sambataro.ignacio.scoreboard.database.asNBADomainModel
import com.sambataro.ignacio.scoreboard.domain.FootballTeamInfo
import com.sambataro.ignacio.scoreboard.domain.NBATeamInfo
import com.sambataro.ignacio.scoreboard.network.TeamsNetwork
import com.sambataro.ignacio.scoreboard.network.asDatabaseModel
import com.sambataro.ignacio.scoreboard.utils.eastConferenceTeams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception


class TeamsRepository(private val database : ScoreBoardDataBase) {

    /**
     * The videos LiveData object is the object who will return the information of the DataBase
     * and it will cal the asDomainModel in order to transform the DataBaseTeams object
     * in a TeamInfo object.
     *
     * This object is automatically update when the DB is updated.
     */
    val teams: LiveData<List<NBATeamInfo>> = Transformations.map(database.teamsDao.getTeams()) {
        reorderStanding(it.asNBADomainModel())
    }

    /**
     * The videos LiveData object is the object who will return the information of the DataBase
     * and it will cal the asDomainModel in order to transform the DataBaseTeams object
     * in a TeamInfo object.
     *
     * This object is automatically update when the DB is updated.
     */
    val footballTeams: LiveData<List<FootballTeamInfo>> = Transformations.map(database
        .teamsDao.getFootballTeams()) {
            reorderFootballStanding(it.asFootballDomainModel())
    }


    /**
     * Refresh the videos stored in the offline cache.
     */
    suspend fun refreshTeams() {
        withContext(Dispatchers.IO) {
            Log.d("SAMBA1", "About to call the getTeamList")
            try {
                val nbaTeamList = TeamsNetwork.teamsResponse.getTeamList("nba").await()
                database.teamsDao.insertAll(nbaTeamList.asDatabaseModel())
                Log.d("SAMBA1", "the awnser of the getTeamList is: " + nbaTeamList.sports[0].id)
            } catch (e : Exception) {
                Log.e("SAMBA1", "error with retrofit: $e")
            }
        }
    }

    /**
     * Refresh the videos stored in the offline cache.
     */
    suspend fun refreshFootballTeams() {
        withContext(Dispatchers.IO) {
            Log.d("SAMBA4", "About to call the getTeamList")
            try {
                val footballTeamList = TeamsNetwork.teamsResponse.getFootballTeamList("arg.1").await()
                database.teamsDao.insertFootballAll(footballTeamList.asDatabaseModel())
                Log.d("SAMBA4", "the awnser of the getTeamList is: " + footballTeamList.sports[0].id)
            } catch (e : Exception) {
                Log.e("SAMBA4", "error with retrofit: $e")
            }
        }
    }

    private fun reorderStanding(teams: List<NBATeamInfo>): List<NBATeamInfo> {
        val eastConference : ArrayList<NBATeamInfo>  = arrayListOf()
        val westConference : ArrayList<NBATeamInfo>  = arrayListOf()
        val resultTeamList: ArrayList<NBATeamInfo> = arrayListOf()
        teams.forEach {
            if (eastConferenceTeams.contains(it.name)) {
                Log.d("SAMBA2", "Equipo EAST: " + it.name)
                eastConference.add(it)
            } else {
                Log.d("SAMBA2", "Equipo WEST: " + it.name)
                westConference.add(it)
            }
        }
        resultTeamList.addAll(eastConference.sortedWith(compareBy{it.playoffseed}))
        Log.d("SAMBA2", "La lista EAST esta ordenada asi: $resultTeamList")
        resultTeamList.addAll(westConference.sortedWith(compareBy{it.playoffseed}))
        Log.d("SAMBA2", "La lista WEST esta ordenada asi: $westConference")

        Log.d("SAMBA2", "La lista tiene un size de:" + resultTeamList.size)


        return resultTeamList
    }

    private fun reorderFootballStanding(teams: List<FootballTeamInfo>): List<FootballTeamInfo> {
        return teams.sortedWith(compareBy{it.position})
    }
}