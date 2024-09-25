package com.example.team25.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToEditWorkLocationActivity()
    }

    private fun navigateToEditWorkLocationActivity() {
        binding.editWorkTimeLocationBtn.setOnClickListener {
            val intent = Intent(this, EditWorkLocationActivity::class.java)
            startActivity(intent)
        }
    }
}
