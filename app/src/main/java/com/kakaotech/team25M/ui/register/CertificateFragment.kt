package com.kakaotech.team25M.ui.register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.kakaotech.team25M.databinding.FragmentCertificateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CertificateFragment : Fragment() {
    private var _binding: FragmentCertificateBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCertificateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initImageLauncher()
        loadInfo()
        observeRegistrationStatus()
        navigateToPrevious()
        setRegisterClickListener()
    }

    private fun loadInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.certificateImage.collect { certificateImageUrl ->
                if (certificateImageUrl.isNotEmpty()) {
                    Glide.with(this@CertificateFragment)
                        .load(certificateImageUrl)
                        .into(binding.certificateUploadBtn)
                }
            }
        }
    }

    private fun initImageLauncher() {
        val getImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.certificateUploadBtn.setImageURI(it)
                binding.addImageImageView.visibility = View.GONE
                binding.addImageTextView.visibility = View.GONE

                registerViewModel.updateCertificateImage(it.toString())
            }
        }

        binding.certificateUploadBtn.setOnClickListener {
            getImageLauncher.launch("image/*")
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            registerViewModel.logManagerInfo()
            parentFragmentManager.popBackStack()
        }
    }

    private fun setRegisterClickListener() {
        binding.registerFinishBtn.setOnClickListener {
            if (registerViewModel.isCertificateImageEmpty()) {
                Toast.makeText(requireContext(), "증명서 이미지를 등록해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                registerViewModel.uploadImage()
                Toast.makeText(requireContext(), "등록 신청 중입니다. 잠시만 기다려주세요.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun observeRegistrationStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.isRegistered.collect { isSuccess ->
                if (isSuccess) {
                    navigateToRegisterStatusActivity()
                }
            }
        }
    }

    private fun navigateToRegisterStatusActivity() {
        val intent = Intent(requireActivity(), RegisterStatusActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
