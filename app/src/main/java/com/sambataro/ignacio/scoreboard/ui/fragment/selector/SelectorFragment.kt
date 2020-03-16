package com.sambataro.ignacio.scoreboard.ui.fragment.selector

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sambataro.ignacio.scoreboard.R
import com.sambataro.ignacio.scoreboard.database.getDatabase
import com.sambataro.ignacio.scoreboard.databinding.SelectorFragmentBinding
import com.sambataro.ignacio.scoreboard.repository.ApplicationRepository
import com.sambataro.ignacio.scoreboard.repository.GamesRepository
import kotlinx.android.synthetic.main.activity_main.*

class SelectorFragment : Fragment() {

    private lateinit var binding: SelectorFragmentBinding

    private lateinit var viewModel: SelectorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val gameScoreRepository = GamesRepository(getDatabase((activity as AppCompatActivity)))
        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).bottom_nav?.visibility = View.GONE

        binding = DataBindingUtil.inflate(inflater,
            R.layout.selector_fragment,
            container, false)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)
            .get(SelectorViewModel::class.java)

        binding.selectorViewModel = viewModel

        viewModel.sendUserToNBAStandingFragment.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController()
                    .navigate(SelectorFragmentDirections.actionShownbaStandingFragment())
                viewModel.displayNBAStandingFragmentDone()

                ApplicationRepository.instance.setLeagueName("nba")
                ApplicationRepository.instance.setSportType("basketball")

                (activity as AppCompatActivity).supportActionBar?.show()
                (activity as AppCompatActivity).bottom_nav?.visibility = View.VISIBLE
            }
        })
        var leagueNameId = String()
        viewModel.leagueNameId.observe(viewLifecycleOwner, Observer {
            leagueNameId = it
        })
        var leagueName = String()
        viewModel.leagueName.observe(viewLifecycleOwner, Observer {
            leagueName = it
        })

        viewModel.sendUserToFootballStandingFragment.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController()
                    .navigate(SelectorFragmentDirections
                        .actionShowFootballStandingFragment(leagueNameId))

                ApplicationRepository.instance.setLeagueName(leagueName)
                ApplicationRepository.instance.setSportType("soccer")

                viewModel.displayFootballStandingFragmentDone()
                (activity as AppCompatActivity).supportActionBar?.show()
                (activity as AppCompatActivity).bottom_nav?.visibility = View.VISIBLE
            }
        })

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as AppCompatActivity).finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        return binding.root
    }
}