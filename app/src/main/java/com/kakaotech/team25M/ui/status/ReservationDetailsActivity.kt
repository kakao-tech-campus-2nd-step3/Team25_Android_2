package com.kakaotech.team25M.ui.status

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kakaotech.team25M.databinding.ActivityReservationDetailsBinding
import com.kakaotech.team25M.domain.model.Gender
import com.kakaotech.team25M.domain.model.ReservationInfo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationDetailsBinding
    private val reservationDetailsViewModel: ReservationDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setReservationInfo()
        collectReservationInfo()

    }

    private fun setReservationInfo() {
        val reservationInfo: ReservationInfo? = intent.getParcelableExtra("ReservationInfo")
        reservationInfo?.let {
            reservationDetailsViewModel.updateReservationInfo(reservationInfo)
        }
    }

    private fun collectReservationInfo() {
        lifecycleScope.launch {
            reservationDetailsViewModel.reservationInfo.collectLatest {
                val dateFormat = SimpleDateFormat("yy.MM.dd a h시", Locale.KOREAN)

                binding.locationDepartTextView.text = it.departure
                binding.locationArriveTextView.text = it.destination
                binding.companionDepartTimeInformationTextView.text =
                    dateFormat.format(it.serviceDate)
                binding.transportationInformationTextView.text = it.transportation
                binding.requestDetailsInformationTextView.text = it.request

                binding.userNameInformationTextView.text = it.patient.patientName
                binding.userGenderInformationTextView.text = when (it.patient.patientGender) {
                    Gender.MALE -> "남"
                    Gender.FEMALE -> "여"
                }
                binding.userBirthInformationTextView.text = it.patient.patientBirth.plus("-*******")
                binding.userPhoneNumberInformationTextView.text = it.patient.patientPhone
            }
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
