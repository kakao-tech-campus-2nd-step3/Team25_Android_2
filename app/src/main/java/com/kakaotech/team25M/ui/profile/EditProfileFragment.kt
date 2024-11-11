package com.kakaotech.team25M.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25M.R
import com.kakaotech.team25M.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val managerInformationViewModel: ManagerInformationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeProfileLoadStatus()
        managerInformationViewModel.getProfile()

        observeInformation()
        navigateToPrevious()
        navigateToEditWorkLocation()
        navigateToEditWorkTime()
        navigateToEditComment()
        navigateToEditProfileImage()
    }

    private fun navigateToEditProfileImage() {
        binding.profileEditBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, EditImageFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeInformation() {
        observeProfileImage()
        observeName()
        observeRegion()
        observeComment()
        observeCareer()
        observeTime()
    }

    private fun observeTime() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    managerInformationViewModel.monStartTime.collect { time ->
                        binding.mondayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.monEndTime.collect { time ->
                        binding.mondayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.tueStartTime.collect { time ->
                        binding.tuesdayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.tueEndTime.collect { time ->
                        binding.tuesdayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.wedStartTime.collect { time ->
                        binding.wednesdayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.wedEndTime.collect { time ->
                        binding.wednesdayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.thuStartTime.collect { time ->
                        binding.thursdayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.thuEndTime.collect { time ->
                        binding.thursdayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.friStartTime.collect { time ->
                        binding.fridayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.friEndTime.collect { time ->
                        binding.fridayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.satStartTime.collect { time ->
                        binding.saturdayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.satEndTime.collect { time ->
                        binding.saturdayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.sunStartTime.collect { time ->
                        binding.sundayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerInformationViewModel.sunEndTime.collect { time ->
                        binding.sundayEndTimeTextView.text = time
                    }
                }
            }
        }
    }

    private fun observeCareer() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerInformationViewModel.career.collect { career ->
                    binding.careerText.text = career
                }
            }
        }
    }

    private fun observeComment() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerInformationViewModel.comment.collect { comment ->
                    binding.introductionTextView.text = comment
                }
            }
        }
    }

    private fun observeRegion() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerInformationViewModel.workingRegion.collect { region ->
                    binding.locationTextView.text = region
                }
            }
        }
    }

    private fun observeName() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerInformationViewModel.name.collect { name ->
                    binding.nameTextView.text = name
                }
            }
        }
    }

    private fun observeProfileImage() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerInformationViewModel.profileImageUri.collect { uri ->
                    uri?.let {
                        binding.profileImageView.background = null
                        binding.profileImageView.setImageURI(it)
                    }
                }
            }
        }
    }

    private fun observeProfileLoadStatus() {
        lifecycleScope.launch {
            managerInformationViewModel.profileLoadStatus.collect { status ->
                when (status) {
                    ManagerInformationViewModel.ProfileLoadStatus.LOADING -> {
                        binding.loadingTextView.text = "로딩 중입니다..."
                    }
                    ManagerInformationViewModel.ProfileLoadStatus.SUCCESS -> {
                        binding.loadingTextView.visibility = View.GONE
                        binding.managerInforLayout.visibility = View.VISIBLE
                    }
                    ManagerInformationViewModel.ProfileLoadStatus.FAILURE -> {
                        binding.loadingTextView.text = "프로필 조회에 실패했습니다."
                    }
                }
            }
        }
    }

    private fun navigateToEditWorkTime() {
        binding.editWorkTimeBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, EditWorkTimeFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun navigateToEditWorkLocation() {
        binding.locationEditBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, EditWorkLocationFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun navigateToEditComment() {
        binding.introductionEditBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, EditCommentFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
