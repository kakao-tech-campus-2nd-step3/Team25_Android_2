package com.example.team25_2.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.team25_2.R
import com.example.team25_2.databinding.ActivityRegisterEntryBinding

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
        }
    }

}
