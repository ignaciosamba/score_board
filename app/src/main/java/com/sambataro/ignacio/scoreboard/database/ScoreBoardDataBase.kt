package com.sambataro.ignacio.scoreboard.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TeamsEntity::class,
                      FootballTeamEntity::class,
                      YesterdayGamesScoreEntity::class,
                      NewsEntity::class],
                      version = 3)
abstract class ScoreBoardDataBase: RoomDatabase() {
    abstract val teamsDao: TeamsDao
}

private lateinit var INSTANCE: ScoreBoardDataBase

fun getDatabase(context: Context): ScoreBoardDataBase {
    synchronized(ScoreBoardDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ScoreBoardDataBase::class.java,
                "teams"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}