package com.sambataro.ignacio.scoreboard.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sambataro.ignacio.scoreboard.database.ScoreBoardDataBase
import com.sambataro.ignacio.scoreboard.database.TeamsEntity
import com.sambataro.ignacio.scoreboard.database.asDomainModel
import com.sambataro.ignacio.scoreboard.domain.TeamInfo
import com.sambataro.ignacio.scoreboard.network.TeamsNetwork
import com.sambataro.ignacio.scoreboard.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TeamsRepository(private val database : ScoreBoardDataBase) {

    /**
     * The videos LiveData object is the object who will return the information of the DataBase
     * and it will cal the asDomainModel in order to transform the DataBaseTeams object
     * in a TeamInfo object.
     *
     * This object is automatically update when the DB is updated.
     */
    val teams: LiveData<List<TeamInfo>> = Transformations.map(database.teamsDao.getTeams()) {
        it.asDomainModel()
    }

    /**
     * Refresh the videos stored in the offline cache.
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = TeamsNetwork.teamsResponse.getTeamList().await()
            database.teamsDao.insertAll(playlist.asDatabaseModel())
        }
    }

}