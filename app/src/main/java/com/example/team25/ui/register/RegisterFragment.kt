package com.example.team25.ui.register

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.team25.R
import com.example.team25.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initImageLauncher()
        navigateToCertificate()
        loadInfo()
    }

    private fun loadInfo() {
        val profileImageUrl = registerViewModel.profileImage.value
        if (profileImageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(Uri.parse(profileImageUrl))
                .into(binding.profileUploadBtn)
        }

        binding.managerNameEditText.setText(registerViewModel.name.value)
        if (registerViewModel.gender.value == "남성") {
            binding.genderManRadioBtn.isChecked = true
        } else {
            binding.genderWomanRadioBtn.isChecked = true
        }
        binding.workHistoryEditText.setText(registerViewModel.career.value)
        binding.introductionEditText.setText(registerViewModel.comment.value)
    }

    private fun initImageLauncher() {
        val getImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.profileUploadBtn.setImageURI(it)
                binding.addImageImageView.visibility = View.GONE
                binding.addImageTextView.visibility = View.GONE

                registerViewModel.updateProfileImage(it.toString())
            }
        }

        binding.profileUploadBtn.setOnClickListener {
            getImageLauncher.launch("image/*")
        }
    }

    private fun navigateToCertificate() {
        binding.nextBtn.setOnClickListener {
            val name = binding.managerNameEditText.text.toString()
            val gender = if (binding.genderManRadioBtn.isChecked) "남성" else "여성"
            val workHistory = binding.workHistoryEditText.text.toString()
            val introduction = binding.introductionEditText.text.toString()

            if (name.isBlank() || workHistory.isBlank() || introduction.isBlank()) {
                Toast.makeText(requireContext(), "모든 필드를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                registerViewModel.updateName(name)
                registerViewModel.updateGender(gender)
                registerViewModel.updateCareer(workHistory)
                registerViewModel.updateComment(introduction)

                registerViewModel.logManagerInfo()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.register_fragment_container, CertificateFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
