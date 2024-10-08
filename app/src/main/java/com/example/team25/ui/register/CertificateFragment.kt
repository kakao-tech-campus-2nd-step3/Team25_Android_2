package com.example.team25.ui.register

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.team25.databinding.FragmentCertificateBinding
import dagger.hilt.android.AndroidEntryPoint

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
        navigateToPrevious()
        navigateToRegisterStatus()
    }

    private fun loadInfo() {
        val certificateImageUrl = registerViewModel.certificateImage.value
        if (certificateImageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(certificateImageUrl)
                .into(binding.certificateUploadBtn)
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

    private fun navigateToRegisterStatus() {
        binding.registerFinishBtn.setOnClickListener {
            val intent = Intent(requireActivity(), RegisterStatusActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
