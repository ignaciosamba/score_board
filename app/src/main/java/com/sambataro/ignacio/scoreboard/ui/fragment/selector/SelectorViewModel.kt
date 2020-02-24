package com.sambataro.ignacio.scoreboard.ui.fragment.selector

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectorViewModel : ViewModel() {


    private val _sendUserToNBAStandingFragment = MutableLiveData<Boolean>()

    val sendUserToNBAStandingFragment : LiveData<Boolean>
        get() = _sendUserToNBAStandingFragment

    private val _sendUserToFootballStandingFragment = MutableLiveData<Boolean>()

    val sendUserToFootballStandingFragment : LiveData<Boolean>
        get() = _sendUserToFootballStandingFragment


    fun nbaClick() {
        _sendUserToNBAStandingFragment.value = true
    }

    fun argFootballClick() {
        _sendUserToFootballStandingFragment.value = true
    }

    fun displayNBAStandingFragmentDone() {
        _sendUserToNBAStandingFragment.value = null
    }

    fun displayFootballStandingFragmentDone() {
        _sendUserToFootballStandingFragment.value = null
    }
}