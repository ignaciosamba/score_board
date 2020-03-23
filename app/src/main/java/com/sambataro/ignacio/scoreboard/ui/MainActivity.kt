package com.sambataro.ignacio.scoreboard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.sambataro.ignacio.scoreboard.R
import com.sambataro.ignacio.scoreboard.ui.fragment.gamescore.GameScoreFragmentDirections
import com.sambataro.ignacio.scoreboard.ui.fragment.standings.FootballStandingFragmentDirections
import com.sambataro.ignacio.scoreboard.ui.fragment.standings.StandingFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment)

        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = navController
        return when(navController.currentDestination?.id) {
            R.id.game_score_id -> {
                navController.navigate(GameScoreFragmentDirections.actionReturnToSelector())
                true
            }
            R.id.standing_id -> {
                navController.navigate(StandingFragmentDirections.actionReturnToSelector())
                true
            }
            R.id.football_standing_id -> {
                navController.navigate(FootballStandingFragmentDirections.actionReturnToSelector())
                true
            }
            R.id.league_news_id-> {
                navController.navigate(FootballStandingFragmentDirections.actionReturnToSelector())
                true
            }
            else -> navController.navigateUp()
        }
    }
}
