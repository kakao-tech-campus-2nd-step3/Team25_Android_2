package com.kakaotech.team25M.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25M.R
import com.kakaotech.team25M.databinding.FragmentEditWorkLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditWorkLocationFragment : Fragment() {
    private var _binding: FragmentEditWorkLocationBinding? = null
    private val binding get() = _binding!!
    private val managerInformationViewModel: ManagerInformationViewModel by activityViewModels()
    private var sido = "서울"
    private var sigungu = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditWorkLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        loadInfo()
        setSidoDropDown()
        navigateToPrevious()
        setupSigunguEditTextListener()
        observeLocationPatchStatus()
        changeLocation()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeLocationPatchStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerInformationViewModel.locationPatched.collect { isPatched ->
                    when (isPatched) {
                        PatchStatus.SUCCESS -> {
                            managerInformationViewModel.updateLocationPatchStatus(PatchStatus.DEFAULT)
                            Toast.makeText(requireContext(), "근무 지역 변경 완료", Toast.LENGTH_SHORT).show()
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                        PatchStatus.FAILURE -> {
                            managerInformationViewModel.updateLocationPatchStatus(PatchStatus.DEFAULT)
                            Toast.makeText(requireContext(), "지역 변경 실패", Toast.LENGTH_SHORT).show()
                        }
                        PatchStatus.DEFAULT -> {}
                    }
                }
            }
        }
    }

    private fun loadInfo() {
        val region = managerInformationViewModel.getRegion()
        if (region != null) {
            sido = region.split(' ')[0]
            sigungu = region.split(' ')[1]

            binding.sigunguEditText.setText(sigungu)
        }
    }

    private fun setSidoDropDown() {
        val sidoOptions = resources.getStringArray(R.array.sido_option)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, sidoOptions)
        binding.sidoAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.sidoAutoCompleteTextView.setText(sido, false)

        binding.sidoAutoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            sido = parent.getItemAtPosition(position).toString()
        }
    }

    private fun setupSigunguEditTextListener() {
        binding.sigunguEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                sigungu = s.toString()
            }
        })
    }

    private fun changeLocation() {
        binding.nextBtn.setOnClickListener {
            if (sido == "" || sigungu == "") {
                Toast.makeText(requireContext(), "변경할 지역을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                managerInformationViewModel.patchLocation(sido, sigungu)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
