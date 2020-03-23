package com.sambataro.ignacio.scoreboard.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sambataro.ignacio.scoreboard.database.ScoreBoardDataBase
import com.sambataro.ignacio.scoreboard.database.asNewsDomainModel
import com.sambataro.ignacio.scoreboard.domain.NewsInfo
import com.sambataro.ignacio.scoreboard.network.TeamsNetwork
import com.sambataro.ignacio.scoreboard.network.asDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository (private val database: ScoreBoardDataBase) {

    private val TAG = "NewsRepository"

    val news: LiveData<List<NewsInfo>> = Transformations.map(database.teamsDao.getNews()) {
        it.asNewsDomainModel()
    }

    suspend fun refreshNews(sport: String, leagueName: String) {
        withContext(Dispatchers.IO) {
            try {
                database.teamsDao.deleteAllNews()

                val newsList = TeamsNetwork.response
                    .getNewsBySport(sport, leagueName).await()

                database.teamsDao.insertAllNews(newsList.asDataBaseModel())
            } catch (e : Exception) {
                Log.e(TAG, "error with retrofit: $e")
            }
        }
    }
}