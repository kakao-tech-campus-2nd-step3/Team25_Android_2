package com.kakaotech.team25M.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25M.databinding.ActivityMainBinding
import com.kakaotech.team25M.ui.companion.LiveCompanionActivity
import com.kakaotech.team25M.ui.login.LoginEntryActivity
import com.kakaotech.team25M.ui.profile.ProfileActivity
import com.kakaotech.team25M.ui.status.ReservationStatusActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeWithdrawEvent()
        observeName()
        checkName()
        navigateToProfile()
        navigateToLiveCompanion()
        navigateToReservationStatus()
        setLogoutClickListener()
        setWithdrawClickListener()
    }

    private fun navigateToProfile() {
        binding.profileImageView.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkName() {
        mainViewModel.getName()
    }

    private fun observeName() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.name.collect { name ->
                    binding.managerNameTextView.text = name
                }
            }
        }
    }


    private fun setLogoutClickListener() {
        binding.logoutTextView.setOnClickListener {
            mainViewModel.logout()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginEntryActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLiveCompanion() {
        binding.realTimeCompanionManageBtn.setOnClickListener {
            val intent = Intent(this, LiveCompanionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToReservationStatus() {
        binding.reservationManageBtn.setOnClickListener {
            val intent = Intent(this, ReservationStatusActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeWithdrawEvent() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.withdrawEvent.collect { event ->
                    when (event) {
                        is WithdrawStatus.Success -> {
                            mainViewModel.logout()
                            navigateToLogin()
                        }

                        is WithdrawStatus.Failure -> {
                            Toast.makeText(this@MainActivity, "회원 탈퇴에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                        }

                        WithdrawStatus.Idle -> {
                        }
                    }
                }
            }
        }
    }

    private fun setWithdrawClickListener() {
        binding.withdrawTextView.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle("회원 탈퇴")
                .setMessage("정말로 회원 탈퇴를 하시겠습니까?")
                .setPositiveButton("확인") { _, _ ->
                    mainViewModel.withdraw()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }

            dialogBuilder.show()

        }
    }
}
