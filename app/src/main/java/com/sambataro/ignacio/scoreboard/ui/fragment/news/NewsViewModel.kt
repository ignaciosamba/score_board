package com.sambataro.ignacio.scoreboard.ui.fragment.news

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sambataro.ignacio.scoreboard.database.getDatabase
import com.sambataro.ignacio.scoreboard.repository.ApplicationRepository
import com.sambataro.ignacio.scoreboard.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

class NewsViewModel (application: Application) : ViewModel(){

    private val newsRepository = NewsRepository(getDatabase(application))

    private val newsViewModelJob = SupervisorJob()

    private val newsViewModelScope = CoroutineScope(newsViewModelJob
            + Dispatchers.Main)

    val news = newsRepository.news

    /**
     * League name.
     */
    val leagueName = ApplicationRepository.instance.leagueName

    /**
     * Sport type.
     */
    val sportType = ApplicationRepository.instance.sportType

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

    init {
        fetchNews()
    }

    fun fetchNews(){
        newsViewModelScope.launch {
            try {
                newsRepository.refreshNews(sportType.value!!, leagueName.value!!)
            } catch (networkError: IOException) {
                if (news.value.isNullOrEmpty())
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
        newsViewModelJob.cancel()
    }

}