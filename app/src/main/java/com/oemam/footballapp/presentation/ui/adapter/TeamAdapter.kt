package com.oemam.footballapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oemam.footballapp.databinding.ItemTeamBinding
import com.oemam.footballapp.core.domain.model.Team

class TeamAdapter(private val onItemClick: (Team) -> Unit) :
    ListAdapter<Team, TeamAdapter.TeamViewHolder>(TeamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TeamViewHolder(private val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team) {
            binding.tvTeamName.text = team.name
            binding.tvStadium.text = team.stadium
            Glide.with(binding.ivBadge.context)
                .load(team.badge)
                .into(binding.ivBadge)
            binding.root.setOnClickListener { onItemClick(team) }
        }
    }

    class TeamDiffCallback : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean = oldItem == newItem
    }
}
