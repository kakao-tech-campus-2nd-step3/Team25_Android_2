package com.example.team25.ui.register

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var getImageLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initImageLauncher()
        setProfileUploadBtnClickListener()
        navigateToCertificate()
    }

    private fun initImageLauncher() {
        getImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.profileUploadBtn.background = Drawable.createFromStream(contentResolver.openInputStream(it), it.toString())
                binding.addImageImageView.visibility = View.GONE
                binding.addImageTextView.visibility = View.GONE
            }
        }
    }

    private fun setProfileUploadBtnClickListener() {
        binding.profileUploadBtn.setOnClickListener {
            getImageLauncher.launch("image/*")
        }
    }

    private fun navigateToCertificate() {
        binding.nextBtn.setOnClickListener {
            val name = binding.managerNameTextView.text.toString()
            val gender = if (binding.genderManRadioBtn.isChecked) "남성" else "여성"
            val workHistory = binding.workHistoryEditText.text.toString()
            val introduction = binding.introductionEditText.text.toString()


            if (name.isBlank() || workHistory.isBlank() || introduction.isBlank()) {
                Toast.makeText(this, "모든 필드를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CertificateActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
