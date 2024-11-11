package com.kakaotech.team25M.ui.status

import android.app.Dialog
import com.kakaotech.team25M.databinding.ViewCompanionCompleteDialogBinding
import com.kakaotech.team25M.domain.model.ReservationInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class CompanionCompleteDialog :
    DialogFragment() {
    private var _binding: ViewCompanionCompleteDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val RESERVATION_INFO = "reservation_info"

        fun newInstance(reservationInfo: ReservationInfo): CompanionCompleteDialog {
            val fragment = CompanionCompleteDialog()
            val args = Bundle().apply {
                putParcelable(RESERVATION_INFO, reservationInfo)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        _binding = ViewCompanionCompleteDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDialogBtnListener()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)
        isCancelable = true
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDialogBtnListener() {
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        binding.navigateToReportWriteBtn.setOnClickListener {
            Toast.makeText(requireContext(), "리포트 작성 화면으로 이동", Toast.LENGTH_SHORT).show()
        }
    }
}
