package com.sambataro.ignacio.scoreboard.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sambataro.ignacio.scoreboard.R
import com.sambataro.ignacio.scoreboard.databinding.FootballStandingFragmentBinding
import com.sambataro.ignacio.scoreboard.databinding.FootballStandingItemBinding
import com.sambataro.ignacio.scoreboard.databinding.StandingItemBinding
import com.sambataro.ignacio.scoreboard.domain.FootballTeamInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1


class FootballStandingAdapter : ListAdapter<FootballDataItem,
        RecyclerView.ViewHolder>(FootballTeamsDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitListFootball(list: List<FootballTeamInfo>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(FootballDataItem.Header)
                else -> listOf(FootballDataItem.Header) + list.map { FootballDataItem.TeamInfoItemFootball(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val teamItem = getItem(position) as FootballDataItem.TeamInfoItemFootball
                holder.bind(teamItem.team)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FootballDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is FootballDataItem.TeamInfoItemFootball -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.football_standing_header, parent, false)
                return TextViewHolder(view)
            }
        }
    }


    class ViewHolder private constructor(val binding: FootballStandingItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: FootballTeamInfo) {
            Log.d("SAMBA1", "ADAPTER, HOLDER ITEMS ES: " + item.name)
            binding.footballTeamInfoStanding = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FootballStandingItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class FootballTeamsDiffCallback : DiffUtil.ItemCallback<FootballDataItem>() {

    override fun areItemsTheSame(oldItem: FootballDataItem, newItem: FootballDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FootballDataItem, newItem: FootballDataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class FootballDataItem {
    data class TeamInfoItemFootball(val team: FootballTeamInfo): FootballDataItem() {
        override val id = team.id.toLong()
    }

    object Header: FootballDataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}
