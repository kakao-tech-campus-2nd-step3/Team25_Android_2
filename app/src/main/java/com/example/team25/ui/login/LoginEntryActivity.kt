package com.example.team25.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityLoginEntryBinding
import com.example.team25.ui.main.MainActivity
import com.example.team25.ui.register.RegisterEntryActivity

class LoginEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToMain()
        navigateToRegisterEntry()
    }

    private fun navigateToMain() {
        binding.appNameTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToRegisterEntry() {
        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, RegisterEntryActivity::class.java)
            startActivity(intent)
        }
    }
}
