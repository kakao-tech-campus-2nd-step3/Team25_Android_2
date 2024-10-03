package com.example.team25.ui.status

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.team25.R
import com.example.team25.databinding.ActivityPaymentHistoryBinding
import com.example.team25.databinding.ActivityReservationDetailsBinding
import com.example.team25.domain.model.Gender
import com.example.team25.domain.model.ReservationInfo
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
