package com.sambataro.ignacio.scoreboard.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sambataro.ignacio.scoreboard.domain.NBATeamInfo


@BindingAdapter("playoffseed")
fun TextView.setPlayoffSeed(item: NBATeamInfo?) {
    item?.let {
        text =  item.playoffseed.toString()
    }
}

@BindingAdapter("logoteam")
fun ImageView.setLogoTeam(item: NBATeamInfo?) {
    item?.let {
        Glide.with(this)
            .load(item.logo)
            .into(this)
    }
}

@BindingAdapter("teamname")
fun TextView.setTeamName(item: NBATeamInfo?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("teamwins")
fun TextView.setTeamWins(item: NBATeamInfo?) {
    item?.let {
        text = item.win.substringBefore(".")
    }
}

@BindingAdapter("teamlosses")
fun TextView.setTeamLosses(item: NBATeamInfo?) {
    item?.let {
        text = item.lose.substringBefore(".")
    }
}

@BindingAdapter("teamwinningpercentage")
fun TextView.setTeamWinningPercentage(item: NBATeamInfo?) {
    item?.let {
        text = item.winningPercentage.substring(0,6)
    }
}

@BindingAdapter("teamgb")
fun TextView.setTeamgb(item: NBATeamInfo?) {
    item?.let {
        text = item.gb
    }
}


