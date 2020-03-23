package com.sambataro.ignacio.scoreboard.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sambataro.ignacio.scoreboard.domain.SportTeamInfo

class ApplicationRepository {

    companion object {
        val instance = ApplicationRepository()
    }

    /**
     * MutableLiveData for update the name of the fragment into the SupportActionBar.
     */
    private var _leagueName = MutableLiveData<String>()

    /**
     * LiveData for update the name of the fragment into the SupportActionBar.
     */
    val leagueName: LiveData<String>
        get() = _leagueName

    /**
     * MutableLiveData for update the name of the fragment into the SupportActionBar.
     */
    private var _leagueId = MutableLiveData<String>()

    /**
     * LiveData for update the name of the fragment into the SupportActionBar.
     */
    val leagueId: LiveData<String>
        get() = _leagueId


    private var _sportType = MutableLiveData<String>()

    val sportType : LiveData<String>
        get() = _sportType

    fun setLeagueName(name: String) {
        _leagueName.value = name
    }

    fun setLeagueId(id: String) {
        _leagueId.value = id
    }

    fun setSportType(sportType: String) {
        _sportType.value = sportType
    }

}