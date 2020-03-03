package com.sambataro.ignacio.scoreboard.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sambataro.ignacio.scoreboard.databinding.GamesScoreItemBinding
import com.sambataro.ignacio.scoreboard.domain.GameScoreInfo

class GameScoreAdapter : ListAdapter<GameScoreInfo, GameScoreAdapter.ViewHolder>(GameScoreDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: GamesScoreItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: GameScoreInfo) {
            binding.gamesScoreInfo = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GamesScoreItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class GameScoreDiffCallback : DiffUtil.ItemCallback<GameScoreInfo>() {

    override fun areItemsTheSame(oldItem: GameScoreInfo, newItem: GameScoreInfo): Boolean {
        return oldItem.game_id == newItem.game_id
    }


    override fun areContentsTheSame(oldItem: GameScoreInfo, newItem: GameScoreInfo): Boolean {
        return oldItem == newItem
    }


}
