package com.kakaotech.team25M.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25M.databinding.FragmentEditImageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditImageFragment : Fragment() {
    private var _binding: FragmentEditImageBinding? = null
    private val binding get() = _binding!!
    private val managerInformationViewModel: ManagerInformationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeImagePatchStatus()
        navigateToPrevious()
        initImageLauncher()
        changeImage()
    }

    private fun observeImagePatchStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerInformationViewModel.imagePatched.collect { isPatched ->
                    when (isPatched) {
                        PatchStatus.SUCCESS -> {
                            managerInformationViewModel.updateImagePatchStatus(PatchStatus.DEFAULT)
                            Toast.makeText(requireContext(), "프로필 이미지 변경 완료", Toast.LENGTH_SHORT).show()
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                        PatchStatus.FAILURE -> {
                            managerInformationViewModel.updateImagePatchStatus(PatchStatus.DEFAULT)
                            Toast.makeText(requireContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show()
                        }
                        PatchStatus.DEFAULT -> {}
                    }
                }
            }
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun changeImage() {
        binding.nextBtn.setOnClickListener {
            if (managerInformationViewModel.isNewProfileImageEmpty()) {
                Toast.makeText(requireContext(), "프로필 이미지를 업로드해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                managerInformationViewModel.uploadImage()
            }
        }
    }

    private fun initImageLauncher() {
        val getImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.profileUploadBtn.setImageURI(it)
                binding.addImageImageView.visibility = View.GONE
                binding.addImageTextView.visibility = View.GONE

                managerInformationViewModel.updateProfileImage(it.toString())
            }
        }

        binding.profileUploadBtn.setOnClickListener {
            getImageLauncher.launch("image/*")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
