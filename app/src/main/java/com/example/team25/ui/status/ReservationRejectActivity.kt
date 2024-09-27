package com.example.team25.ui.status

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityReservationRejectBinding

class ReservationRejectActivity : AppCompatActivity() {
    private lateinit var binding : ActivityReservationRejectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationRejectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
