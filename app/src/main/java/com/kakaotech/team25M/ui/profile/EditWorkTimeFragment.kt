package com.kakaotech.team25M.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25M.R
import com.kakaotech.team25M.domain.model.WorkTimeDomain
import com.kakaotech.team25M.databinding.FragmentEditWorkTimeBinding
import com.kakaotech.team25M.utils.DropdownUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class EditWorkTimeFragment : Fragment() {
    private var _binding: FragmentEditWorkTimeBinding? = null
    private val binding get() = _binding!!
    private val managerInformationViewModel: ManagerInformationViewModel by activityViewModels()
    private var workTime: WorkTimeDomain = WorkTimeDomain()
    private lateinit var autoCompleteTextViews : List<AutoCompleteTextView>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditWorkTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        loadInfo()
        initializeAutoCompleteTextViews()
        observeTimePatchStatus()
        navigateToPrevious()
        changeTime()
    }

    private fun loadInfo() {
        workTime = managerInformationViewModel.getTime()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initializeAutoCompleteTextViews() {
        autoCompleteTextViews = listOf(
            binding.mondayStartTimeAutoCompleteTextView,
            binding.mondayEmdTimeAutoCompleteTextView,
            binding.tuesdayStartTimeAutoCompleteTextView,
            binding.tuesEmdTimeAutoCompleteTextView,
            binding.wednesdayStartTimeAutoCompleteTextView,
            binding.wednesdayEmdTimeAutoCompleteTextView,
            binding.thursdayStartTimeAutoCompleteTextView,
            binding.thursdayEmdTimeAutoCompleteTextView,
            binding.fridayStartTimeAutoCompleteTextView,
            binding.fridayEmdTimeAutoCompleteTextView,
            binding.saturdayStartTimeAutoCompleteTextView,
            binding.saturdayEmdTimeAutoCompleteTextView,
            binding.sundayStartTimeAutoCompleteTextView,
            binding.sundayEmdTimeAutoCompleteTextView
        )

        setupDropdownAndListeners()

        binding.mondayStartTimeAutoCompleteTextView.setText(workTime.monStartTime, false)
        binding.mondayEmdTimeAutoCompleteTextView.setText(workTime.monEndTime, false)
        binding.tuesdayStartTimeAutoCompleteTextView.setText(workTime.tueStartTime, false)
        binding.tuesEmdTimeAutoCompleteTextView.setText(workTime.tueEndTime, false)
        binding.wednesdayStartTimeAutoCompleteTextView.setText(workTime.wedStartTime, false)
        binding.wednesdayEmdTimeAutoCompleteTextView.setText(workTime.wedEndTime, false)
        binding.thursdayStartTimeAutoCompleteTextView.setText(workTime.thuStartTime, false)
        binding.thursdayEmdTimeAutoCompleteTextView.setText(workTime.thuEndTime, false)
        binding.fridayStartTimeAutoCompleteTextView.setText(workTime.friStartTime, false)
        binding.fridayEmdTimeAutoCompleteTextView.setText(workTime.friEndTime, false)
        binding.saturdayStartTimeAutoCompleteTextView.setText(workTime.satStartTime, false)
        binding.saturdayEmdTimeAutoCompleteTextView.setText(workTime.satEndTime, false)
        binding.sundayStartTimeAutoCompleteTextView.setText(workTime.sunStartTime, false)
        binding.sundayEmdTimeAutoCompleteTextView.setText(workTime.sunEndTime, false)
    }

    private fun setupDropdownAndListeners() {
        autoCompleteTextViews.forEachIndexed { index, autoCompleteTextView ->
            DropdownUtils.setupDropdown(requireContext(), autoCompleteTextView, R.array.time)
            autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                val selectedTime = autoCompleteTextView.adapter.getItem(position).toString()
                updateWorkTime(index, selectedTime)
            }
        }
    }

    private fun updateWorkTime(index: Int, selectedTime: String) {
        when (index) {
            0 -> workTime.monStartTime = selectedTime
            1 -> workTime.monEndTime = selectedTime
            2 -> workTime.tueStartTime = selectedTime
            3 -> workTime.tueEndTime = selectedTime
            4 -> workTime.wedStartTime = selectedTime
            5 -> workTime.wedEndTime = selectedTime
            6 -> workTime.thuStartTime = selectedTime
            7 -> workTime.thuEndTime = selectedTime
            8 -> workTime.friStartTime = selectedTime
            9 -> workTime.friEndTime = selectedTime
            10 -> workTime.satStartTime = selectedTime
            11 -> workTime.satEndTime = selectedTime
            12 -> workTime.sunStartTime = selectedTime
            13 -> workTime.sunEndTime = selectedTime
        }
    }

    private fun observeTimePatchStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerInformationViewModel.timePatched.collect { isPatched ->
                    when (isPatched) {
                        PatchStatus.SUCCESS -> {
                            managerInformationViewModel.updateTimePatchStatus(PatchStatus.DEFAULT)
                            Toast.makeText(requireContext(), "근무 시간 변경 완료", Toast.LENGTH_SHORT).show()
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                        PatchStatus.FAILURE -> {
                            managerInformationViewModel.updateTimePatchStatus(PatchStatus.DEFAULT)
                            Toast.makeText(requireContext(), "시간 변경 실패", Toast.LENGTH_SHORT).show()
                        }
                        PatchStatus.DEFAULT -> {}
                    }
                }
            }
        }
    }

    private fun changeTime() {
        binding.nextBtn.setOnClickListener {
            normalizeWorkTime(workTime)
            if (isValidWorkTime(workTime)) {
                managerInformationViewModel.patchTime(workTime)
            } else {
                Toast.makeText(requireContext(), "각 요일의 시작 시간이 종료 시간보다 이전이어야 합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidWorkTime(workTime: WorkTimeDomain): Boolean {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return try {
            val mondayStart = timeFormat.parse(workTime.monStartTime)!!
            val mondayEnd = timeFormat.parse(workTime.monEndTime)!!
            val tuesdayStart = timeFormat.parse(workTime.tueStartTime)!!
            val tuesdayEnd = timeFormat.parse(workTime.tueEndTime)!!
            val wednesdayStart = timeFormat.parse(workTime.wedStartTime)!!
            val wednesdayEnd = timeFormat.parse(workTime.wedEndTime)!!
            val thursdayStart = timeFormat.parse(workTime.thuStartTime)!!
            val thursdayEnd = timeFormat.parse(workTime.thuEndTime)!!
            val fridayStart = timeFormat.parse(workTime.friStartTime)!!
            val fridayEnd = timeFormat.parse(workTime.friEndTime)!!
            val saturdayStart = timeFormat.parse(workTime.satStartTime)!!
            val saturdayEnd = timeFormat.parse(workTime.satEndTime)!!
            val sundayStart = timeFormat.parse(workTime.sunStartTime)!!
            val sundayEnd = timeFormat.parse(workTime.sunEndTime)!!

            !mondayStart.after(mondayEnd) &&
                !tuesdayStart.after(tuesdayEnd) &&
                !wednesdayStart.after(wednesdayEnd) &&
                !thursdayStart.after(thursdayEnd) &&
                !fridayStart.after(fridayEnd) &&
                !saturdayStart.after(saturdayEnd) &&
                !sundayStart.after(sundayEnd)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun normalizeWorkTime(workTime: WorkTimeDomain) {
        if (workTime.monStartTime == workTime.monEndTime && workTime.monStartTime != "00:00") {
            workTime.monStartTime = "00:00"
            workTime.monEndTime = "00:00"
        }
        if (workTime.tueStartTime == workTime.tueEndTime && workTime.tueStartTime != "00:00") {
            workTime.tueStartTime = "00:00"
            workTime.tueEndTime = "00:00"
        }
        if (workTime.wedStartTime == workTime.wedEndTime && workTime.wedStartTime != "00:00") {
            workTime.wedStartTime = "00:00"
            workTime.wedEndTime = "00:00"
        }
        if (workTime.thuStartTime == workTime.thuEndTime && workTime.thuStartTime != "00:00") {
            workTime.thuStartTime = "00:00"
            workTime.thuEndTime = "00:00"
        }
        if (workTime.friStartTime == workTime.friEndTime && workTime.friStartTime != "00:00") {
            workTime.friStartTime = "00:00"
            workTime.friEndTime = "00:00"
        }
        if (workTime.satStartTime == workTime.satEndTime && workTime.satStartTime != "00:00") {
            workTime.satStartTime = "00:00"
            workTime.satEndTime = "00:00"
        }
        if (workTime.sunStartTime == workTime.sunEndTime && workTime.sunStartTime != "00:00") {
            workTime.sunStartTime = "00:00"
            workTime.sunEndTime = "00:00"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

