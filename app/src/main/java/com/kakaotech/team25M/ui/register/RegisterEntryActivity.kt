package com.kakaotech.team25M.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakaotech.team25M.databinding.ActivityRegisterEntryBinding

class RegisterEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToRegister()
    }

    private fun navigateToRegister() {
        binding.registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
