package com.kakaotech.team25M.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

object DropdownUtils {

    fun setupDropdown(
        context: Context,
        autoCompleteTextView: AutoCompleteTextView,
        stringArrayResId: Int
    ) {
        // 리소스에서 배열을 가져옴
        val items = context.resources.getStringArray(stringArrayResId)

        // ArrayAdapter 설정
        val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, items)
        autoCompleteTextView.setAdapter(adapter)

        // AutoCompleteTextView 클릭 시 드롭다운을 열도록 설정
        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.showDropDown()
        }
    }
}
