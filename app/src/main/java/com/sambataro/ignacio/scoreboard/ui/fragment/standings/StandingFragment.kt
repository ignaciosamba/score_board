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
import androidx.lifecycle.ViewModelProviders
import com.sambataro.ignacio.scoreboard.R
import com.sambataro.ignacio.scoreboard.databinding.StandingFragmentBinding
import com.sambataro.ignacio.scoreboard.domain.TeamInfo
import com.sambataro.ignacio.scoreboard.ui.fragment.splash.SplashViewModelFactory

class StandingFragment : Fragment(){

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

        viewModel.teams.observe(viewLifecycleOwner, Observer<List<TeamInfo>> {teams->
            teams.apply {
                Log.d("SAMBA1", "trying with a list of: " + teams.size)
                teams.forEach {
                    Log.d("SAMBA1", "TEAMS: " + it.name + "\n")
                }
            }
        })

        return binding.root
    }
}