package com.example.team25.ui.status

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.databinding.ActivityPaymentHistoryBinding
import com.example.team25.domain.model.PaymentInfo
import com.example.team25.ui.status.adapter.CompanionHistoryRecyclerViewAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PaymentHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentHistoryBinding
    private val viewModel: PaymentHistoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setCompanionHistoryRecyclerViewAdapter()
        setObserves()
    }

    private fun setObserves(){
        collectPaymentInfo()
        collectCompanionHistory()
    }

    private fun collectPaymentInfo(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.paymentInfo.collectLatest { paymentInfo ->
                    bindPaymentInfo(paymentInfo)
                }
            }
        }
    }

    private fun collectCompanionHistory(){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.companionHistory.collectLatest {
                    (binding.companionHistoryRecyclerView.adapter as? CompanionHistoryRecyclerViewAdapter)?.submitList(it)
                }
            }
        }
    }

    private fun bindPaymentInfo(paymentInfo: PaymentInfo) {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREAN)

        binding.paymentTimeInformationTextView.text = dateFormat.format(paymentInfo.paymentDate)
        binding.paymentMethodInformationTextView.text = paymentInfo.paymentMethod
        binding.cashReceiptInformationTextView.text = if(paymentInfo.cashReceipt) "현금 영수증 발행" else "현금 영수증 미발행"
        binding.totalPaymentAmountInformationTextView.text =
            paymentInfo.paymentAmount.toString().plus(" 원")
    }

    private fun setCompanionHistoryRecyclerViewAdapter() {
        val adapter = CompanionHistoryRecyclerViewAdapter()
        binding.companionHistoryRecyclerView.adapter = adapter
        binding.companionHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
