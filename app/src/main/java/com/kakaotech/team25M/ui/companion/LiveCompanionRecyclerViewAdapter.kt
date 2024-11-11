package com.kakaotech.team25M.ui.companion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakaotech.team25M.databinding.ItemLiveCompanionStatusBinding

class LiveCompanionRecyclerViewAdapter :
    ListAdapter<String, LiveCompanionRecyclerViewAdapter.LiveCompanionViewHolder>(DiffCallback()) {
    class LiveCompanionViewHolder(val binding: ItemLiveCompanionStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.liveCompanionStatusItem.text = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LiveCompanionViewHolder {
        val binding = ItemLiveCompanionStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LiveCompanionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LiveCompanionViewHolder,
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
