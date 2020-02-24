package com.sambataro.ignacio.scoreboard.ui.fragment.standings

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sambataro.ignacio.scoreboard.database.getDatabase
import com.sambataro.ignacio.scoreboard.repository.TeamsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

class StandingViewModel(application: Application) : ViewModel() {

    /**
     * We will use this repository to fetch the data.
     */
    private val teamsRepository = TeamsRepository(getDatabase(application))

    /**
     * Teams that will be displayed on the NBA Standing.
     */
    val teams = teamsRepository.teams

    /**
     * Teams that will be displayed on the Football Standing.
     */
    val footballTeams = teamsRepository.footballTeams

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val standingViewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val standingViewModelScope = CoroutineScope(standingViewModelJob + Dispatchers.Main)

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val networkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        fetchDataFromRepository()
    }

    private fun fetchDataFromRepository() {
        standingViewModelScope.launch {
            try {
                teamsRepository.refreshTeams()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                if (teams.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }


    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        standingViewModelJob.cancel()
    }

}