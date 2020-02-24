package com.sambataro.ignacio.scoreboard.ui.fragment.standings

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
import com.sambataro.ignacio.scoreboard.databinding.StandingFragmentBinding
import com.sambataro.ignacio.scoreboard.domain.FootballTeamInfo
import com.sambataro.ignacio.scoreboard.domain.NBATeamInfo
import com.sambataro.ignacio.scoreboard.ui.adapters.FootballStandingAdapter
import com.sambataro.ignacio.scoreboard.ui.adapters.StandingAdapter

class FootballStandingFragment : Fragment(){

    private lateinit var binding: StandingFragmentBinding

    private lateinit var viewModel: StandingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(inflater, R.layout.standing_fragment,
            container, false)

        binding.lifecycleOwner = this

        val standingViewModelFactory = StandingViewModelFactory(application)
        viewModel = ViewModelProvider(this, standingViewModelFactory)
            .get(StandingViewModel::class.java)

        binding.standingViewModel = viewModel

        val adapter = FootballStandingAdapter()
        binding.standingList.adapter = adapter

        viewModel.footballTeams.observe(viewLifecycleOwner, Observer<List<FootballTeamInfo>> {
                footballTeams->
            footballTeams.let {
                Log.d("SAMBA4", "FootballStandingFragment, observer: " + footballTeams.size)
                adapter.addHeaderAndSubmitListFootball(footballTeams)
            }
        })

        val manager = LinearLayoutManager(activity)
        binding.standingList.layoutManager = manager

        return binding.root
    }

}