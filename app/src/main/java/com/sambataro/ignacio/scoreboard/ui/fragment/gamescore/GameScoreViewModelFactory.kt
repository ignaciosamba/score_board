package com.sambataro.ignacio.scoreboard.ui.fragment.gamescore

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sambataro.ignacio.scoreboard.ui.fragment.splash.SplashViewModel

class GameScoreViewModelFactory(val app: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameScoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameScoreViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}