package com.sambataro.ignacio.scoreboard.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sambataro.ignacio.scoreboard.R
import com.sambataro.ignacio.scoreboard.databinding.FootballStandingItemBinding
import com.sambataro.ignacio.scoreboard.databinding.StandingItemBinding
import com.sambataro.ignacio.scoreboard.domain.FootballTeamInfo
import com.sambataro.ignacio.scoreboard.domain.NBATeamInfo
import com.sambataro.ignacio.scoreboard.domain.SportTeamInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val ITEM_VIEW_TYPE_HEADER_NBA = 0
private val ITEM_VIEW_TYPE_HEADER_FOOTBALL = 1
private val ITEM_VIEW_TYPE_ITEM_NBA = 2
private val ITEM_VIEW_TYPE_ITEM_FOOTBALL = 3

class StandingAdapterSuper : ListAdapter<DataItem2, RecyclerView.ViewHolder>(TeamsDiffCallback2()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitListNBA(list: List<NBATeamInfo>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem2.HeaderNBA)
                else -> listOf(DataItem2.HeaderNBA) + list.map { DataItem2.NBATeamInfoItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    fun addHeaderAndSubmitListFootball(list: List<FootballTeamInfo>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem2.HeaderFootball)
                else -> listOf(DataItem2.HeaderFootball) + list.map { DataItem2.FootballTeamInfoItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderNBA -> {
                val teamItem = getItem(position) as DataItem2.NBATeamInfoItem
                holder.bind(teamItem.team)
            }
            is ViewHolderFootball -> {
                val teamItem = getItem(position) as DataItem2.FootballTeamInfoItem
                holder.bind(teamItem.team)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER_NBA -> TextViewHolderNBA.from(parent)
            ITEM_VIEW_TYPE_HEADER_FOOTBALL -> TextViewHolderFootball.from(parent)
            ITEM_VIEW_TYPE_ITEM_NBA -> ViewHolderNBA.from(parent)
            ITEM_VIEW_TYPE_ITEM_FOOTBALL -> ViewHolderFootball.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {

        val type =  when (getItem(position)) {
            DataItem2.HeaderNBA -> ITEM_VIEW_TYPE_HEADER_NBA
            DataItem2.HeaderFootball -> ITEM_VIEW_TYPE_HEADER_FOOTBALL
            else -> if (getItem(position).sportType == SportTeamInfo.SPORTTYPE.NBA) {
                ITEM_VIEW_TYPE_ITEM_NBA
            } else {
                ITEM_VIEW_TYPE_ITEM_FOOTBALL
            }
        }
        return type
    }

    class TextViewHolderNBA(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolderNBA {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.standing_header, parent, false)
                return TextViewHolderNBA(view)
            }
        }
    }

    class TextViewHolderFootball(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolderFootball {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.football_standing_header, parent, false)
                return TextViewHolderFootball(view)
            }
        }
    }


    class ViewHolderNBA private constructor(val binding: StandingItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: NBATeamInfo) {
            Log.d("SAMBA1", "ADAPTER, HOLDER ITEMS ES: " + item.team_name)
            binding.teamInfoStanding = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolderNBA {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StandingItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolderNBA(binding)
            }
        }
    }

    class ViewHolderFootball private constructor(val binding: FootballStandingItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: FootballTeamInfo) {
            Log.d("SAMBA1", "ADAPTER, HOLDER ITEMS ES: " + item.name)
            binding.footballTeamInfoStanding = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolderFootball {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FootballStandingItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolderFootball(binding)
            }
        }
    }
}

class TeamsDiffCallback2 : DiffUtil.ItemCallback<DataItem2>() {

    override fun areItemsTheSame(oldItem: DataItem2, newItem: DataItem2): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem2, newItem: DataItem2): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem2 {
    data class NBATeamInfoItem(val team: NBATeamInfo): DataItem2() {
        override val id = team.team_id.toLong()
        override val sportType = team.sportType
    }

    data class FootballTeamInfoItem(val team: FootballTeamInfo): DataItem2() {
        override val id = team.team_id.toLong()
        override val sportType = team.sportType
    }

    object HeaderNBA: DataItem2() {
        override val id = Long.MIN_VALUE
        override val sportType = ""
    }

    object HeaderFootball: DataItem2() {
        override val id = Long.MIN_VALUE
        override val sportType = ""
    }

    abstract val id: Long
    abstract val sportType : String
}