package com.sambataro.ignacio.scoreboard.ui.fragment.gamescore

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sambataro.ignacio.scoreboard.R
import com.sambataro.ignacio.scoreboard.databinding.GamesScoresFragmentBinding
import com.sambataro.ignacio.scoreboard.domain.DayCalendarInfo
import com.sambataro.ignacio.scoreboard.ui.adapters.CalendarAdapter
import com.sambataro.ignacio.scoreboard.ui.adapters.GameScoreAdapter
import com.sambataro.ignacio.scoreboard.ui.adapters.SelectDayListener
import java.text.SimpleDateFormat
import java.util.*

class GameScoreFragment : Fragment(){

    private lateinit var binding: GamesScoresFragmentBinding

    private lateinit var viewModel: GameScoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(inflater, R.layout.games_scores_fragment,
            container, false)

        binding.lifecycleOwner = this

        val gameScoreViewModelFactory = GameScoreViewModelFactory(application)
        viewModel = ViewModelProvider(this, gameScoreViewModelFactory)
            .get(GameScoreViewModel::class.java)

        binding.gamesScoreViewModel = viewModel

        val adapter = GameScoreAdapter()
        binding.gameScoreList.adapter = adapter

        val calendarAdapter = context?.let {
            CalendarAdapter(it, SelectDayListener { date ->
                viewModel.onSelectDayClicked(date)
            })
        }
        binding.gameScoreCalendar.adapter = calendarAdapter

        val manager = LinearLayoutManager(activity)
        val manager2 = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        binding.gameScoreList.layoutManager = manager
        binding.gameScoreCalendar.layoutManager = manager2

        viewModel.games.observe(viewLifecycleOwner, Observer { games->
            games?.let {
                adapter.submitList(games)
            }
        })

        viewModel.calendarList.observe(viewLifecycleOwner, Observer {calendarInfo ->
            calendarAdapter?.let {
                manager2.initialPrefetchItemCount = findTodays(calendarInfo)
                // 3 is the amount of items I need to move in order to center the today date
                // in the middle of the screen.
                manager2.scrollToPosition(findTodays(calendarInfo) - 3)
                calendarAdapter.submitList(calendarInfo)
            }

        })

        return binding.root
    }

    private fun findTodays(calendarList: List<DayCalendarInfo>) : Int{
        var position = 0
        var returnPosition = 0
        val dateFormat = SimpleDateFormat("EEE, dd MMM. yyyy", Locale.getDefault())
        val dateFormatToSelectDay = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val calendarToSelectDay = Calendar.getInstance()
        val date = dateFormat.format(calendar.time).toString().replace("-", "")
        val dateToShow = dateFormatToSelectDay.format(calendarToSelectDay.time).toString()
        val dayCal = DayCalendarInfo(date.substringBefore(","),
            date.substringAfter(",").substringBefore("."),
            dateToShow, false)
        calendarList.forEach {
            position += 1
            if (it.dateInfo == dayCal.dateInfo) {
                returnPosition = position
            }
        }
        return returnPosition
    }
}