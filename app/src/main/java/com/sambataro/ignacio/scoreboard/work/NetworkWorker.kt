package com.sambataro.ignacio.scoreboard.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sambataro.ignacio.scoreboard.database.ScoreBoardDataBase
import com.sambataro.ignacio.scoreboard.database.getDatabase
import com.sambataro.ignacio.scoreboard.repository.TeamsRepository
import retrofit2.HttpException

class NetworkWorker(appContext: Context, params: WorkerParameters) :
        CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "NetworkWorker"
    }
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = TeamsRepository(database)

        try {
            repository.refreshVideos( )
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }

}