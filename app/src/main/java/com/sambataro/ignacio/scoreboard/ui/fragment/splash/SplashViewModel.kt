package com.sambataro.ignacio.scoreboard.ui.fragment.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sambataro.ignacio.scoreboard.database.getDatabase
import com.sambataro.ignacio.scoreboard.repository.GamesRepository
import com.sambataro.ignacio.scoreboard.repository.TeamsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

class SplashViewModel(application: Application) : ViewModel() {

    /**
     * We will use this repository to fetch the data.
     */
    private val teamsRepository = TeamsRepository(getDatabase(application))

    private val gamesRepository = GamesRepository(getDatabase(application))

    /**
     * Teams that will be displayed on the Standing.
     */
    val teams = teamsRepository.teams


    val games = gamesRepository.games

    private val splashViewModelJob = SupervisorJob()

    private val splashViewModelScope = CoroutineScope(splashViewModelJob +
            Dispatchers.Main)

    private val _sendUserToSelectorFragment = MutableLiveData<Boolean>()

    val sendUserToSelectorFragment : LiveData<Boolean>
        get() = _sendUserToSelectorFragment

    init {
        fetchTeamsData()
    }

    private fun fetchTeamsData() {
        splashViewModelScope.launch {
            try {
                teamsRepository.refreshTeams()
                teamsRepository.refreshFootballTeams()
//                gamesRepository.refreshYesterdayGames()
                _sendUserToSelectorFragment.value = true
            } catch (networkError: IOException) {
                Log.e("SplashViewModel", "Error fetching data: $networkError")
            }
        }
    }

    fun displayMainFragmentDone() {
        _sendUserToSelectorFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        splashViewModelJob.cancel()
    }
}