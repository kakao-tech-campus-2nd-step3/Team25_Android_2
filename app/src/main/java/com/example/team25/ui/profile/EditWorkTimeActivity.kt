package com.example.team25.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.team25.R
import com.example.team25.databinding.ActivityEditWorkTimeBinding
import com.example.team25.dto.DaySchedule
import com.example.team25.utils.DropdownUtils

class EditWorkTimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditWorkTimeBinding
    private lateinit var dayButtonLayoutPairs: Map<View, View>
    private lateinit var daySchedules: DaySchedule
    private lateinit var autoCompleteTextViews : MutableList<AutoCompleteTextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditWorkTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeAutoCompleteTextViews()
        initializeDayLayoutPairs()

        daySchedules = DaySchedule()
        setupDropdowns()
        activateTimeRange()
        setupNavigation()
    }

    private fun activateTimeRange() {
        dayButtonLayoutPairs.forEach { (button, layout) ->
            button.setOnClickListener {
                if (layout.visibility == View.VISIBLE) {
                    layout.visibility = View.GONE
                    button.setBackgroundResource(R.drawable.before_select_day_of_week)
                    (button as TextView).setTextColor(
                        ContextCompat.getColor(button.context, R.color.blue)
                    )

                    resetDayTime(button.tag as? String)
                } else {
                    layout.visibility = View.VISIBLE
                    button.setBackgroundResource(R.drawable.after_select_day_of_week)
                    (button as TextView).setTextColor(
                        ContextCompat.getColor(button.context, R.color.white)
                    )
                }
            }
        }
    }

    private fun initializeAutoCompleteTextViews() {
        autoCompleteTextViews = mutableListOf(
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

        autoCompleteTextViews.forEach { autoCompleteTextView ->
            DropdownUtils.setupDropdown(this, autoCompleteTextView, R.array.time)
            autoCompleteTextView.setText("00:00")
        }
    }

    private fun initializeDayLayoutPairs() {
        dayButtonLayoutPairs = mapOf(
            binding.mondayBtn.apply { tag = "Monday" } to binding.mondayTimeLayout,
            binding.tuesdayBtn.apply { tag = "Tuesday" } to binding.tuesdayTimeLayout,
            binding.wednesdayBtn.apply { tag = "Wednesday" } to binding.wednesdayTimeLayout,
            binding.thursdayBtn.apply { tag = "Thursday" } to binding.thursdayTimeLayout,
            binding.fridayBtn.apply { tag = "Friday" } to binding.fridayTimeLayout,
            binding.saturdayBtn.apply { tag = "Saturday" } to binding.saturdayTimeLayout,
            binding.sundayBtn.apply { tag = "Sunday" } to binding.sundayTimeLayout
        )
    }

    private fun setupNavigation() {
        binding.previousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.editBtn.setOnClickListener {
            updateSchedules()
            Log.d("EditWorkTimeActivity", "Day Schedules: $daySchedules")
        }
    }
    private fun setupDropdowns() {

        autoCompleteTextViews.forEach { autoCompleteTextView ->
            DropdownUtils.setupDropdown(this, autoCompleteTextView, R.array.time)
        }
    }

    private fun updateSchedules() {
        dayButtonLayoutPairs.entries.forEach { (button, layout) ->
            when (button.tag as String) {
                "Monday" -> {
                    daySchedules.monStartTime = binding.mondayStartTimeAutoCompleteTextView.text.toString()
                    daySchedules.monEndTime = binding.mondayEmdTimeAutoCompleteTextView.text.toString()
                }
                "Tuesday" -> {
                    daySchedules.tueStartTime = binding.tuesdayStartTimeAutoCompleteTextView.text.toString()
                    daySchedules.tueEndTime = binding.tuesEmdTimeAutoCompleteTextView.text.toString()
                }
                "Wednesday" -> {
                    daySchedules.wedStartTime = binding.wednesdayStartTimeAutoCompleteTextView.text.toString()
                    daySchedules.wedEndTime = binding.wednesdayEmdTimeAutoCompleteTextView.text.toString()
                }
                "Thursday" -> {
                    daySchedules.thuStartTime = binding.thursdayStartTimeAutoCompleteTextView.text.toString()
                    daySchedules.thuEndTime = binding.thursdayEmdTimeAutoCompleteTextView.text.toString()
                }
                "Friday" -> {
                    daySchedules.friStartTime = binding.fridayStartTimeAutoCompleteTextView.text.toString()
                    daySchedules.friEndTime = binding.fridayEmdTimeAutoCompleteTextView.text.toString()
                }
                "Saturday" -> {
                    daySchedules.satStartTime = binding.saturdayStartTimeAutoCompleteTextView.text.toString()
                    daySchedules.satEndTime = binding.saturdayEmdTimeAutoCompleteTextView.text.toString()
                }
                "Sunday" -> {
                    daySchedules.sunStartTime = binding.sundayStartTimeAutoCompleteTextView.text.toString()
                    daySchedules.sunEndTime = binding.sundayEmdTimeAutoCompleteTextView.text.toString()
                }
            }
        }
    }
    private fun resetAutoCompleteTextView(startTimeView: AutoCompleteTextView, endTimeView: AutoCompleteTextView) {

        startTimeView.setText("00:00", false)
        endTimeView.setText("00:00", false)


        DropdownUtils.setupDropdown(this, startTimeView, R.array.time)
        DropdownUtils.setupDropdown(this, endTimeView, R.array.time)
    }
    private fun resetDayTime(day: String?) {
        when (day) {
            "Monday" -> {
                daySchedules.monStartTime = "00:00"
                daySchedules.monEndTime = "00:00"
                resetAutoCompleteTextView(binding.mondayStartTimeAutoCompleteTextView, binding.mondayEmdTimeAutoCompleteTextView)
            }
            "Tuesday" -> {
                daySchedules.tueStartTime = "00:00"
                daySchedules.tueEndTime = "00:00"
                resetAutoCompleteTextView(binding.tuesdayStartTimeAutoCompleteTextView, binding.tuesEmdTimeAutoCompleteTextView)
            }
            "Wednesday" -> {
                daySchedules.wedStartTime = "00:00"
                daySchedules.wedEndTime = "00:00"
                resetAutoCompleteTextView(binding.wednesdayStartTimeAutoCompleteTextView, binding.wednesdayEmdTimeAutoCompleteTextView)
            }
            "Thursday" -> {
                daySchedules.thuStartTime = "00:00"
                daySchedules.thuEndTime = "00:00"
                resetAutoCompleteTextView(binding.thursdayStartTimeAutoCompleteTextView, binding.thursdayEmdTimeAutoCompleteTextView)
            }
            "Friday" -> {
                daySchedules.friStartTime = "00:00"
                daySchedules.friEndTime = "00:00"
                resetAutoCompleteTextView(binding.fridayStartTimeAutoCompleteTextView, binding.fridayEmdTimeAutoCompleteTextView)
            }
            "Saturday" -> {
                daySchedules.satStartTime = "00:00"
                daySchedules.satEndTime = "00:00"
                resetAutoCompleteTextView(binding.saturdayStartTimeAutoCompleteTextView, binding.saturdayEmdTimeAutoCompleteTextView)
            }
            "Sunday" -> {
                daySchedules.sunStartTime = "00:00"
                daySchedules.sunEndTime = "00:00"
                resetAutoCompleteTextView(binding.sundayStartTimeAutoCompleteTextView, binding.sundayEmdTimeAutoCompleteTextView)
            }
        }
    }

}

