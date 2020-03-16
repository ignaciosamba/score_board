package com.sambataro.ignacio.scoreboard.ui.fragment.selector

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sambataro.ignacio.scoreboard.utils.LeaguesIds

class SelectorViewModel : ViewModel() {


    private val _sendUserToNBAStandingFragment = MutableLiveData<Boolean>()

    val sendUserToNBAStandingFragment : LiveData<Boolean>
        get() = _sendUserToNBAStandingFragment

    private val _sendUserToFootballStandingFragment = MutableLiveData<Boolean>()

    val sendUserToFootballStandingFragment : LiveData<Boolean>
        get() = _sendUserToFootballStandingFragment

    var _leagueNameId = MutableLiveData<String>()

    val leagueNameId : LiveData<String>
        get() = _leagueNameId

    var _leagueName = MutableLiveData<String>()

    val leagueName : LiveData<String>
        get() = _leagueName

    fun nbaClick() {
        _sendUserToNBAStandingFragment.value = true
    }

    fun arg1FootballClick() {
        _leagueNameId.value = LeaguesIds.SUPER_LIGA_ARGENTINA
        _leagueName.value = "arg.1"
        _sendUserToFootballStandingFragment.value = true
    }

    fun arg2FootballClick() {
        _leagueNameId.value = LeaguesIds.PRIMER_B_NACIONAL
        _leagueName.value = "arg.2"
        _sendUserToFootballStandingFragment.value = true
    }

    fun displayNBAStandingFragmentDone() {
        _sendUserToNBAStandingFragment.value = null
    }

    fun displayFootballStandingFragmentDone() {
        _sendUserToFootballStandingFragment.value = null
    }
}