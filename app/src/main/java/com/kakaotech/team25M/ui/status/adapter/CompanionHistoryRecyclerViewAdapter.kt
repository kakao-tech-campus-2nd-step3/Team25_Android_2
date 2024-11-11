package com.kakaotech.team25M.ui.status.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakaotech.team25M.databinding.ItemCompanionHistoryBinding

class CompanionHistoryRecyclerViewAdapter:
    ListAdapter<String, CompanionHistoryRecyclerViewAdapter.CompanionHistoryViewHolder>(DiffCallback()) {
    class CompanionHistoryViewHolder(val binding: ItemCompanionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.companionHistoryItem.text = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CompanionHistoryViewHolder {
        val binding =
            ItemCompanionHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanionHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CompanionHistoryViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
