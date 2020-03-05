package com.sambataro.ignacio.scoreboard.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sambataro.ignacio.scoreboard.database.ScoreBoardDataBase
import com.sambataro.ignacio.scoreboard.database.asFootballDomainModel
import com.sambataro.ignacio.scoreboard.database.asNBADomainModel
import com.sambataro.ignacio.scoreboard.domain.FootballTeamInfo
import com.sambataro.ignacio.scoreboard.domain.NBATeamInfo
import com.sambataro.ignacio.scoreboard.network.TeamsNetwork
import com.sambataro.ignacio.scoreboard.network.asDatabaseModel
import com.sambataro.ignacio.scoreboard.network.getBasketBallLeagueName
import com.sambataro.ignacio.scoreboard.network.getFootBallLeagueName
import com.sambataro.ignacio.scoreboard.utils.eastConferenceTeams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception


class TeamsRepository(private val database : ScoreBoardDataBase) {

    private val TAG = "TeamsRepository"

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
     * MutableLiveData for update the name of the fragment into the SupportActionBar.
     */
    private var _nbaLeagueName = MutableLiveData<String>()

    /**
     * LiveData for update the name of the fragment into the SupportActionBar.
     */
    val nbaLeagueName: LiveData<String>
        get() = _nbaLeagueName

    /**
     * MutableLiveData for update the name of the fragment into the SupportActionBar.
     */
    private var _footballLeagueName = MutableLiveData<String>()

    /**
     * LiveData for update the name of the fragment into the SupportActionBar.
     */
    val footballLeagueName: LiveData<String>
        get() = _footballLeagueName

    /**
     * Refresh the videos stored in the offline cache.
     */
    suspend fun refreshTeams() {
        withContext(Dispatchers.IO) {
            try {
                val nbaTeamList = TeamsNetwork.teamsResponse.getTeamList("nba").await()
                database.teamsDao.insertAll(nbaTeamList.asDatabaseModel())
                withContext(Dispatchers.Main) {
                    _nbaLeagueName.value = nbaTeamList.getBasketBallLeagueName()
                }
            } catch (e : Exception) {
                Log.e(TAG, "error with retrofit: $e")
            }
        }
    }

    /**
     * Refresh the videos stored in the offline cache.
     */
    suspend fun refreshFootballTeams() {
        withContext(Dispatchers.IO) {
            try {
                val footballTeamList = TeamsNetwork.teamsResponse.getFootballTeamList("arg.1").await()
                database.teamsDao.insertFootballAll(footballTeamList.asDatabaseModel())
                withContext(Dispatchers.Main) {
                    _footballLeagueName.value = footballTeamList.getFootBallLeagueName()
                }
            } catch (e : Exception) {
                Log.e(TAG, "error with retrofit: $e")
            }
        }
    }

    /**
     * Reorder the Nba Teams for display it in the standing.
     *
     * @Param: teams, all the nba teams retrieve from the API.
     * @return All the teams reorder by conference and playOff seed.
     */
    private fun reorderStanding(teams: List<NBATeamInfo>): List<NBATeamInfo> {
        val eastConference : ArrayList<NBATeamInfo>  = arrayListOf()
        val westConference : ArrayList<NBATeamInfo>  = arrayListOf()
        val resultTeamList: ArrayList<NBATeamInfo> = arrayListOf()
        teams.forEach {
            if (eastConferenceTeams.contains(it.name)) {
                eastConference.add(it)
            } else {
                westConference.add(it)
            }
        }
        resultTeamList.addAll(eastConference.sortedWith(compareBy{it.playoffseed}))
        resultTeamList.addAll(westConference.sortedWith(compareBy{it.playoffseed}))

        return resultTeamList
    }

    /**
     * Reorder Football Teams for display it in the standing.
     *
     * @Param: teams, all the football teams retrieve from the API.
     * @return All the teams reorder position.
     */
    private fun reorderFootballStanding(teams: List<FootballTeamInfo>): List<FootballTeamInfo> {
        //TODO: If two teams have the same amount of points we must order them by goals.
        return teams.sortedWith(compareBy{it.position})
    }
}