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
    private lateinit var binding : ActivityEditWorkTimeBinding
    private lateinit var dayButtonLayoutPairs: Map<View, View>
    private lateinit var autoCompleteTextViews : MutableList<AutoCompleteTextView>
    private val daySchedules = mutableListOf<DaySchedule>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditWorkTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeAutoCompleteTextViews()
        setupDropdowns()
        initializeDaySchedules()
        initializeDayLayoutPairs()

        activateTimeRange()


        navigateForward()
        navigateBack()
    }
    private fun activateTimeRange() {
        dayButtonLayoutPairs.forEach { (button, layout) ->
            button.setOnClickListener {
                layout.visibility = if (layout.visibility == View.VISIBLE){
                    resetTimeTextForLayout(layout)
                    button.setBackgroundResource(R.drawable.before_select_day_of_week)
                    (button as TextView).setTextColor(
                        ContextCompat.getColor(button.context, R.color.blue))

                    View.GONE
                }
                else{
                    button.setBackgroundResource(R.drawable.after_select_day_of_week)
                    resetTimeTextForLayout(layout)
                    (button as TextView).setTextColor(
                        ContextCompat.getColor(button.context, R.color.white))
                    View.VISIBLE

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
    }
    private fun initializeDaySchedules() {
        daySchedules.addAll(
            listOf(
                DaySchedule("월"),
                DaySchedule("화"),
                DaySchedule("수"),
                DaySchedule("목"),
                DaySchedule("금"),
                DaySchedule("토"),
                DaySchedule("일")
            )
        )
    }
    private fun updateSchedules() {
        dayButtonLayoutPairs.entries.toList().forEachIndexed { index, (button, layout) ->
            val startTime = getStartTimeForDay(index)
            val endTime = getEndTimeForDay(index)

            daySchedules[index].start_time = startTime
            daySchedules[index].end_time = endTime
        }
    }
    private fun getStartTimeForDay(index: Int): String {
        return when (index) {
            0 -> binding.mondayStartTimeAutoCompleteTextView.text.toString()
            1 -> binding.tuesdayStartTimeAutoCompleteTextView.text.toString()
            2 -> binding.wednesdayStartTimeAutoCompleteTextView.text.toString()
            3 -> binding.thursdayStartTimeAutoCompleteTextView.text.toString()
            4 -> binding.fridayStartTimeAutoCompleteTextView.text.toString()
            5 -> binding.saturdayStartTimeAutoCompleteTextView.text.toString()
            6 -> binding.sundayStartTimeAutoCompleteTextView.text.toString()
            else -> "00:00"
        }
    }
    private fun getEndTimeForDay(index: Int): String {
        return when (index) {
            0 -> binding.mondayEmdTimeAutoCompleteTextView.text.toString()
            1 -> binding.tuesEmdTimeAutoCompleteTextView.text.toString()
            2 -> binding.wednesdayEmdTimeAutoCompleteTextView.text.toString()
            3 -> binding.thursdayEmdTimeAutoCompleteTextView.text.toString()
            4 -> binding.fridayEmdTimeAutoCompleteTextView.text.toString()
            5 -> binding.saturdayEmdTimeAutoCompleteTextView.text.toString()
            6 -> binding.sundayEmdTimeAutoCompleteTextView.text.toString()
            else -> "00:00"
        }
    }


    private fun initializeDayLayoutPairs() {
        dayButtonLayoutPairs = mapOf(
            binding.mondayBtn to binding.mondayTimeLayout,
            binding.tuesdayBtn to binding.tuesdayTimeLayout,
            binding.wednesdayBtn to binding.wednesdayTimeLayout,
            binding.thursdayBtn to binding.thursdayTimeLayout,
            binding.fridayBtn to binding.fridayTimeLayout,
            binding.saturdayBtn to binding.saturdayTimeLayout,
            binding.sundayBtn to binding.sundayTimeLayout
        )
    }


    private fun navigateBack() {

        binding.previousBtn.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun navigateForward() {


        binding.editBtn.setOnClickListener {
            updateSchedules()
            Log.d("123123", "navigateForward: ${daySchedules}")
       }
    }
    private fun setupDropdowns() {

        autoCompleteTextViews.forEach { autoCompleteTextView ->
            DropdownUtils.setupDropdown(this, autoCompleteTextView, R.array.time)
        }
    }
    private fun resetTimeTextForLayout(layout: View) {
        val autoCompleteTextViews = listOf(
            binding.mondayStartTimeAutoCompleteTextView to binding.mondayTimeLayout,
            binding.mondayEmdTimeAutoCompleteTextView to binding.mondayTimeLayout,
            binding.tuesdayStartTimeAutoCompleteTextView to binding.tuesdayTimeLayout,
            binding.tuesEmdTimeAutoCompleteTextView to binding.tuesdayTimeLayout,
            binding.wednesdayStartTimeAutoCompleteTextView to binding.wednesdayTimeLayout,
            binding.wednesdayEmdTimeAutoCompleteTextView to binding.wednesdayTimeLayout,
            binding.thursdayStartTimeAutoCompleteTextView to binding.thursdayTimeLayout,
            binding.thursdayEmdTimeAutoCompleteTextView to binding.thursdayTimeLayout,
            binding.fridayStartTimeAutoCompleteTextView to binding.fridayTimeLayout,
            binding.fridayEmdTimeAutoCompleteTextView to binding.fridayTimeLayout,
            binding.saturdayStartTimeAutoCompleteTextView to binding.saturdayTimeLayout,
            binding.saturdayEmdTimeAutoCompleteTextView to binding.saturdayTimeLayout,
            binding.sundayStartTimeAutoCompleteTextView to binding.sundayTimeLayout,
            binding.sundayEmdTimeAutoCompleteTextView to binding.sundayTimeLayout
        )

        autoCompleteTextViews
            .filter { it.second == layout }
            .forEach { it.first.setText("00:00", false) }

        updateSchedules()
    }

}

