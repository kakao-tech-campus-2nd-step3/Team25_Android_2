package com.kakaotech.team25M.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25M.databinding.FragmentEditCommentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditCommentFragment : Fragment() {
    private var _binding: FragmentEditCommentBinding? = null
    private val binding get() = _binding!!
    private val managerInformationViewModel: ManagerInformationViewModel by activityViewModels()
    private var comment = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        loadInfo()
        navigateToPrevious()
        observeCommentPatchStatus()
        setupIntroductionEditTextListener()
        changeComment()
    }

    private fun loadInfo() {
        val loadedComment = managerInformationViewModel.getComment()
        if (loadedComment != null) {
            comment = loadedComment
        }
        binding.introductionEditText.setText(comment)
    }

    private fun observeCommentPatchStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerInformationViewModel.commentPatched.collect { isPatched ->
                    when (isPatched) {
                        PatchStatus.SUCCESS -> {
                            managerInformationViewModel.updateCommentPatchStatus(PatchStatus.DEFAULT)
                            Toast.makeText(requireContext(), "한 마디 변경 완료", Toast.LENGTH_SHORT).show()
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                        PatchStatus.FAILURE -> {
                            managerInformationViewModel.updateCommentPatchStatus(PatchStatus.DEFAULT)
                            Toast.makeText(requireContext(), "한 마디 변경 실패", Toast.LENGTH_SHORT).show()
                        }
                        PatchStatus.DEFAULT -> {}
                    }
                }
            }
        }
    }

    private fun setupIntroductionEditTextListener() {
        binding.introductionEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                comment = s.toString()
            }
        })
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun changeComment() {
        binding.nextBtn.setOnClickListener {
            if (comment.isEmpty()) {
                Toast.makeText(requireContext(), "변경할 한 마디를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                managerInformationViewModel.patchComment(comment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
