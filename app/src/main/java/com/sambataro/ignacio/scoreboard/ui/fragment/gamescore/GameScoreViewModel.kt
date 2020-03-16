package com.sambataro.ignacio.scoreboard.ui.fragment.gamescore

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sambataro.ignacio.scoreboard.database.getDatabase
import com.sambataro.ignacio.scoreboard.domain.DayCalendarInfo
import com.sambataro.ignacio.scoreboard.repository.ApplicationRepository
import com.sambataro.ignacio.scoreboard.repository.GamesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GameScoreViewModel(application: Application) : ViewModel() {

    private val gameScoreRepository = GamesRepository(getDatabase(application))

    /**
     * League name that will be displayed on the NBA Standing as Title.
     */
    val leagueName = ApplicationRepository.instance.leagueName

    val sportType = ApplicationRepository.instance.sportType

    val games = gameScoreRepository.games

    private val gamesScoreViewModelJob = SupervisorJob()

    private val gamesScoreViewModelScope = CoroutineScope(gamesScoreViewModelJob
                                            + Dispatchers.Main)

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

    private var _calendarList = MutableLiveData<List<DayCalendarInfo>>()

    val calendarList : LiveData<List<DayCalendarInfo>>
        get() = _calendarList

    private var _dayToShowGames = MutableLiveData<String>()

    val dayToShowGames : LiveData<String>
        get() = _dayToShowGames

    fun onSelectDayClicked(date: String) {
        _dayToShowGames.value = date
        fetchDataFromRepository()
    }

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        fetchDataFromRepository()
        getYearCalendar()
    }

    private fun getYearCalendar() {
        var listToSave = ArrayList<DayCalendarInfo>()
        val dateFormat = SimpleDateFormat("EEE, dd MMM. yyyy", Locale.getDefault())
        val dateFormatToSelectDay = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val calendarToSelectDay = Calendar.getInstance()
        calendar.set(2020,0,1)
        calendarToSelectDay.set(2020,0,1)
        for (i in 1..365){
            calendar.add(Calendar.DATE, 1)
            val date = dateFormat.format(calendar.time).toString().replace("-", "")

            calendarToSelectDay.add(Calendar.DATE, 1)
            val dateToShow = dateFormatToSelectDay.format(calendarToSelectDay.time).toString()
                .replace("-", "")

            val dayCal = DayCalendarInfo(date.substringBefore(","),
                date.substringAfter(",").substringBefore("."),
                dateToShow, false)

            listToSave.add(DayCalendarInfo(date.substringBefore(","),
                                           date.substringAfter(",").substringBefore("."),
                                           dateToShow, false))

        }
        _calendarList.value = listToSave
    }


    private fun fetchDataFromRepository() {
        gamesScoreViewModelScope.launch {
            try {
                if (dayToShowGames.value != null) {
                    gameScoreRepository.refreshGamesByDay(sportType.value!!,
                                    leagueName.value!!, dayToShowGames.value!!)
                } else {
                    gameScoreRepository.refreshYesterdayGames(sportType.value!!, leagueName.value!!)
                }
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                if (games.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        gamesScoreViewModelJob.cancel()
    }
}