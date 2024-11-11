package com.kakaotech.team25M.ui.status.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakaotech.team25M.databinding.ItemCompanionCompleteHistoryBinding
import com.kakaotech.team25M.domain.model.ReservationInfo
import com.kakaotech.team25M.ui.status.interfaces.OnShowDetailsClickListener
import java.text.SimpleDateFormat
import java.util.Locale

class CompanionCompleteHistoryRecyclerViewAdapter (
    private val detailsClickListener: OnShowDetailsClickListener
) :
    ListAdapter<
        ReservationInfo,
        CompanionCompleteHistoryRecyclerViewAdapter.CompanionCompleteViewHolder,
        >(DiffCallback()) {
    class CompanionCompleteViewHolder(
        private val binding: ItemCompanionCompleteHistoryBinding,
        private val detailsClickListener: OnShowDetailsClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTextView.text = item.patient.patientName
            binding.reservationDateTextView.text = dateFormat.format(item.serviceDate)

            binding.showDetailsBtn.setOnClickListener {
                detailsClickListener.onDetailsClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CompanionCompleteViewHolder {
        val binding = ItemCompanionCompleteHistoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanionCompleteViewHolder(binding, detailsClickListener)
    }

    override fun onBindViewHolder(
        holder: CompanionCompleteViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<ReservationInfo>() {
        override fun areItemsTheSame(
            oldItem: ReservationInfo,
            newItem: ReservationInfo,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ReservationInfo,
            newItem: ReservationInfo,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
