package com.sambataro.ignacio.scoreboard.ui.fragment.gamescore

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
import com.sambataro.ignacio.scoreboard.databinding.StandingFragmentBinding
import com.sambataro.ignacio.scoreboard.domain.GameScoreInfo
import com.sambataro.ignacio.scoreboard.domain.NBATeamInfo
import com.sambataro.ignacio.scoreboard.ui.adapters.GameScoreAdapter
import com.sambataro.ignacio.scoreboard.ui.adapters.StandingAdapter
import com.sambataro.ignacio.scoreboard.ui.fragment.standings.StandingViewModel
import com.sambataro.ignacio.scoreboard.ui.fragment.standings.StandingViewModelFactory

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

        viewModel.games.observe(viewLifecycleOwner, Observer { games->
            games?.let {
                Log.d("SAMBA1", "StandingFragment, observer: " + games.size)
                adapter.submitList(games)
            }
        })

        val manager = LinearLayoutManager(activity)
        binding.gameScoreList.layoutManager = manager

        return binding.root
    }
}