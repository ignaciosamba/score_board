package com.sambataro.ignacio.scoreboard.ui.fragment.standings

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sambataro.ignacio.scoreboard.R
import com.sambataro.ignacio.scoreboard.databinding.StandingFragmentBinding
import com.sambataro.ignacio.scoreboard.domain.FootballTeamInfo
import com.sambataro.ignacio.scoreboard.ui.adapters.StandingAdapterSuper
import kotlinx.android.synthetic.main.activity_main.*

class FootballStandingFragment : Fragment(){

    private lateinit var binding: StandingFragmentBinding

    private lateinit var viewModel: StandingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).bottom_nav.menu.getItem(0).isVisible = true
        (activity as AppCompatActivity).bottom_nav.menu.getItem(1).isVisible = false

        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(inflater, R.layout.standing_fragment,
            container, false)

        binding.lifecycleOwner = this

        val standingViewModelFactory = StandingViewModelFactory(application)
        viewModel = ViewModelProvider(this, standingViewModelFactory)
            .get(StandingViewModel::class.java)

        binding.standingViewModel = viewModel

        val adapter = StandingAdapterSuper()
        binding.standingList.adapter = adapter

        var leagueId = String()
        viewModel.footballLeagueId.observe(viewLifecycleOwner, Observer {
            leagueId = it
        })

        viewModel.footballTeams.observe(viewLifecycleOwner, Observer {
                footballTeams->
            footballTeams.let {
                viewModel.fetchFootballTeamByLeague(ArrayList<FootballTeamInfo>(footballTeams), leagueId)
                adapter.addHeaderAndSubmitListFootball(viewModel.footballTeamsByLeague.value)
            }
        })

        viewModel.footBallLeague.observe(viewLifecycleOwner, Observer {league ->
            league?.let {
                (activity as AppCompatActivity).supportActionBar?.title = league
            }
        })

        val manager = LinearLayoutManager(activity)
        binding.standingList.layoutManager = manager

        return binding.root
    }

}