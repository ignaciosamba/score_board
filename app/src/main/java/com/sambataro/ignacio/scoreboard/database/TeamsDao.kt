package com.sambataro.ignacio.scoreboard.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TeamsDao {
    @Query("select * from nba_teams_table")
    fun getTeams(): LiveData<List<TeamsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( teams: List<TeamsEntity>)

    @Query("select * from football_teams_table")
    fun getFootballTeams(): LiveData<List<FootballTeamEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFootballAll( teams: List<FootballTeamEntity>)
}