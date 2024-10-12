package com.example.team25.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityMainBinding
import com.example.team25.ui.companion.LiveCompanionActivity
import com.example.team25.ui.status.ReservationStatusActivity
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        navigateToLiveCompanion()
        navigateToReservationStatus()
    }

    private fun navigateToLiveCompanion() {
        binding.realTimeCompanionManageBtn.setOnClickListener {
            val intent = Intent(this, LiveCompanionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToReservationStatus() {
        binding.reservationManageBtn.setOnClickListener {
            val intent = Intent(this, ReservationStatusActivity::class.java)
            startActivity(intent)
        }
    }
}
