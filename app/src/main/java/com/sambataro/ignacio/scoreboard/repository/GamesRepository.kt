package com.sambataro.ignacio.scoreboard.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sambataro.ignacio.scoreboard.database.ScoreBoardDataBase
import com.sambataro.ignacio.scoreboard.database.asGameScoreDomainModel
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

    val games: LiveData<List<GameScoreInfo>> = Transformations.map(database.teamsDao.getGamesYesterday()) {
        it.asGameScoreDomainModel()
    }

    /**
     * Refresh the videos stored in the offline cache.
     */
    suspend fun refreshYesterdayGames() {
        withContext(Dispatchers.IO) {
            Log.d("SAMBA5", "About to call the getYesterdayGames")
            try {
                val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -1)
                val date = dateFormat.format(calendar.time).toString().replace("-", "")
                Log.d("SAMBA5", "Date is: $date")
                val yesterdayGamesScoreList = TeamsNetwork.teamsResponse.getBasketballGamesForYesterday("nba", date).await()
                database.teamsDao.insertGamesYesterdayAll(yesterdayGamesScoreList.asDataBaseModel())
                Log.d("SAMBA5", "the awnser of the getBasketballGamesForYesterday is: " +
                        yesterdayGamesScoreList.events.size)
            } catch (e : Exception) {
                Log.e("SAMBA5", "error with retrofit: $e")
            }
        }
    }
}