package com.kakaotech.team25M.ui.status.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakaotech.team25M.databinding.ItemReservationStatusBinding
import com.kakaotech.team25M.domain.model.AccompanyInfo
import com.kakaotech.team25M.ui.status.interfaces.OnCompanionStartClickListener
import com.kakaotech.team25M.ui.status.interfaces.OnShowDetailsClickListener
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationStatusRecyclerViewAdapter (
    private val companionStartClickListener: OnCompanionStartClickListener,
    private val detailsClickListener: OnShowDetailsClickListener,
) : ListAdapter<
    AccompanyInfo,
    ReservationStatusRecyclerViewAdapter.ReservationStatusViewHolder,
    >(DiffCallback()) {

    class ReservationStatusViewHolder(
        private val binding: ItemReservationStatusBinding,
        private val companionStartClickListener: OnCompanionStartClickListener,
        private val detailsClickListener: OnShowDetailsClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AccompanyInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)
            val isRunning = item.isRunningService
            binding.reservationDateTextView.text = dateFormat.format(item.reservationInfo.serviceDate)
            binding.userNameTextView.text = item.reservationInfo.patient.patientName


            if (isRunning) {
                binding.companionStartBtn.visibility = View.GONE
                binding.companionCompleteBtn.visibility = View.VISIBLE
            } else {
                binding.companionStartBtn.visibility = View.VISIBLE
                binding.companionCompleteBtn.visibility = View.GONE
            }

            binding.companionStartBtn.setOnClickListener {
                companionStartClickListener.onStartClicked(item)
            }

            binding.companionCompleteBtn.setOnClickListener {
                companionStartClickListener.onCompleteClicked(item)
            }

            binding.showDetailsBtn.setOnClickListener {
                detailsClickListener.onDetailsClicked(item.reservationInfo)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationStatusViewHolder {
        val binding =
            ItemReservationStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationStatusViewHolder(
            binding,
            companionStartClickListener,
            detailsClickListener,
        )
    }

    override fun onBindViewHolder(
        holder: ReservationStatusViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<AccompanyInfo>() {
        override fun areItemsTheSame(
            oldItem: AccompanyInfo,
            newItem: AccompanyInfo,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AccompanyInfo,
            newItem: AccompanyInfo,
        ): Boolean {
            return oldItem.isRunningService == newItem.isRunningService
        }
    }
}
