package com.sambataro.ignacio.scoreboard.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sambataro.ignacio.scoreboard.domain.FootballTeamInfo

@BindingAdapter("footballteamposition")
fun TextView.setPosition(item: FootballTeamInfo?) {
    item?.let {
        text =  item.position.toString()
    }
}

@BindingAdapter("footballlogoteam")
fun ImageView.setLogoTeam(item: FootballTeamInfo?) {
    item?.let {
        Glide.with(this)
            .load(item.logo)
            .into(this)
    }
}

@BindingAdapter("footballteamname")
fun TextView.setTeamName(item: FootballTeamInfo?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("footballteamwins")
fun TextView.setTeamWins(item: FootballTeamInfo?) {
    item?.let {
        text = item.win.substringBefore(".")
    }
}

@BindingAdapter("footballteamlosses")
fun TextView.setTeamLosses(item: FootballTeamInfo?) {
    item?.let {
        text = item.lose.substringBefore(".")
    }
}

@BindingAdapter("footballteamgamesplayed")
fun TextView.setTeamGamesPlayed(item: FootballTeamInfo?) {
    item?.let {
        text = item.gamesPlayed.substringBefore(".")
    }
}

@BindingAdapter("footballTeampoints")
fun TextView.setTeamPoints(item: FootballTeamInfo?) {
    item?.let {
        text = item.points.substringBefore(".")
    }
}