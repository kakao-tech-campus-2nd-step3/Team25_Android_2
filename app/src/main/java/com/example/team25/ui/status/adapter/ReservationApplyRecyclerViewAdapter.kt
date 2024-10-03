package com.example.team25.ui.status.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.team25.databinding.ItemReservationApplyBinding
import com.example.team25.domain.model.ReservationInfo
import com.example.team25.ui.status.interfaces.OnReservationApplyClickListener
import com.example.team25.ui.status.interfaces.OnShowDetailsClickListener
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationApplyRecyclerViewAdapter (
    private val reservationApplyClickListener: OnReservationApplyClickListener,
    private val detailsClickListener: OnShowDetailsClickListener
) :
    ListAdapter<
        ReservationInfo,
        ReservationApplyRecyclerViewAdapter.ReservationApplyViewHolder,
        >(DiffCallback()) {
    class ReservationApplyViewHolder(
        private val binding: ItemReservationApplyBinding,
        private val reservationApplyClickListener: OnReservationApplyClickListener,
        private val detailsClickListener: OnShowDetailsClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTextView.text = item.patient.patientName
            binding.reservationDateTextView.text = dateFormat.format(item.serviceDate)

            binding.acceptBtn.setOnClickListener {
                reservationApplyClickListener.onAcceptClicked(item)
            }

            binding.refuseBtn.setOnClickListener {
                reservationApplyClickListener.onRefuseClicked(item)
            }

            binding.showDetailsBtn.setOnClickListener {
                detailsClickListener.onDetailsClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationApplyViewHolder {
        val binding =
            ItemReservationApplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationApplyViewHolder(
            binding,
            reservationApplyClickListener,
            detailsClickListener
        )
    }

    override fun onBindViewHolder(
        holder: ReservationApplyViewHolder,
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
