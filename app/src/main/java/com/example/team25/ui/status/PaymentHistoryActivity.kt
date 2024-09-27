package com.example.team25.ui.status

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityPaymentHistoryBinding

class PaymentHistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
