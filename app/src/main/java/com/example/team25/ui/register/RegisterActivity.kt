package com.example.team25.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToCertificate()
        navigateToRegisterStatus()
    }

    private fun navigateToCertificate() {
        binding.goCertificationBtn.setOnClickListener {
            val intent = Intent(this, CertificateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToRegisterStatus() {
        binding.goRegisterBtn.setOnClickListener {
            val intent = Intent(this, RegisterStatusActivity::class.java)
            startActivity(intent)
        }
    }
}
