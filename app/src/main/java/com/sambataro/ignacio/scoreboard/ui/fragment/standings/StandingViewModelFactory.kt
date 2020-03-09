package com.sambataro.ignacio.scoreboard.ui.fragment.standings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StandingViewModelFactory(private val application: Application,
                               private val leagueId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StandingViewModel::class.java)) {
            return StandingViewModel(application, leagueId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}