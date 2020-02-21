package com.sambataro.ignacio.scoreboard.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sambataro.ignacio.scoreboard.domain.TeamInfo

@Dao
interface TeamsDao {
    @Query("select * from teams_table")
    fun getTeams(): LiveData<List<TeamsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( teams: List<TeamsEntity>)
}