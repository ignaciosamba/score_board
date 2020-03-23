package com.sambataro.ignacio.scoreboard.ui.fragment.standings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StandingViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StandingViewModel::class.java)) {
            return StandingViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}