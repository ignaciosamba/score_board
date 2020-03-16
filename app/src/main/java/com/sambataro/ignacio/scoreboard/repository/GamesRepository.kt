package com.sambataro.ignacio.scoreboard.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sambataro.ignacio.scoreboard.database.ScoreBoardDataBase
import com.sambataro.ignacio.scoreboard.database.asGameScoreDomainModel
import com.sambataro.ignacio.scoreboard.database.getDatabase
import com.sambataro.ignacio.scoreboard.domain.GameScoreInfo
import com.sambataro.ignacio.scoreboard.network.TeamsNetwork
import com.sambataro.ignacio.scoreboard.network.asDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


/**
 * Default date format
 */
const val DATE_FORMAT = "yyyy-MM-dd"

class GamesRepository(private val database: ScoreBoardDataBase) {

    private val TAG = "GamesRepository"

    val games: LiveData<List<GameScoreInfo>> = Transformations.map(database.teamsDao.getGamesYesterday()) {
        it.asGameScoreDomainModel()
    }

    /**
     * Refresh the videos stored in the offline cache.
     */
    suspend fun refreshYesterdayGames(sport: String, leagueName: String) {
        withContext(Dispatchers.IO) {
            try {
                database.teamsDao.deleteAllGames()
                val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -1)
                val date = dateFormat.format(calendar.time).toString().replace("-", "")

                val yesterdayGamesScoreList = TeamsNetwork.teamsResponse
                    .getBasketballGamesForYesterday(sport, leagueName, date).await()

                database.teamsDao.insertGamesYesterdayAll(yesterdayGamesScoreList.asDataBaseModel())
            } catch (e : Exception) {
                Log.e(TAG, "error with retrofit: $e")
            }
        }
    }

    suspend fun refreshGamesByDay(sport: String, leagueName: String, day: String) {
        withContext(Dispatchers.IO) {
            try {
                database.teamsDao.deleteAllGames()

                val yesterdayGamesScoreList = TeamsNetwork.teamsResponse
                    .getBasketballGamesForYesterday(sport, leagueName, day).await()

                database.teamsDao.insertGamesYesterdayAll(yesterdayGamesScoreList.asDataBaseModel())
            } catch (e : Exception) {
                Log.e(TAG, "error with retrofit: $e")
            }
        }
    }
}