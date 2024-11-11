package com.kakaotech.team25M.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25M.databinding.ActivityLoginEntryBinding
import com.kakaotech.team25M.domain.model.UserStatus
import com.kakaotech.team25M.ui.main.MainActivity
import com.kakaotech.team25M.ui.register.RegisterEntryActivity
import com.kakaotech.team25M.ui.register.RegisterStatusActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Instant

@AndroidEntryPoint
class LoginEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEntryBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeLoginState()
        checkAutoLogin()
        setKakaoLoginBtnClickListener()
    }

    private fun checkAutoLogin() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val tokens = loginViewModel.getSavedTokens()
                if (tokens != null && tokens.accessToken.isNotEmpty()) {
                    val currentTime = Instant.now().epochSecond * 1000
                    val expirationTime = tokens.loginTime * 1000 + tokens.refreshTokenExpiresIn
                    val timeThreshold = 7 * 24 * 60 * 60000L

                    if (currentTime >= expirationTime - timeThreshold) {
                        loginViewModel.logout()
                        binding.kakaoLoginBtn.visibility = View.VISIBLE
                        return@repeatOnLifecycle
                    } else {
                        val status = loginViewModel.getUserStatus()
                        Log.d("testt", status.toString())
                        navigateBasedOnRoleAuto(status)
                    }
                } else {
                    binding.kakaoLoginBtn.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun checkRole() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val status = loginViewModel.getUserStatus()
                Log.d("testt", status.toString())
                navigateBasedOnRoleDefault(status)
            }
        }
    }

    private fun navigateBasedOnRoleAuto(status: UserStatus?) {
        when (status) {
            UserStatus.MANAGER -> navigateToMain()
            UserStatus.MANAGER_PENDING -> navigateToRegisterStatus()
            UserStatus.USER -> navigateToRegisterEntry()
            else -> {
                binding.kakaoLoginBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun navigateBasedOnRoleDefault(status: UserStatus?) {
        when (status) {
            UserStatus.MANAGER -> navigateToMain()
            UserStatus.MANAGER_PENDING -> navigateToRegisterStatus()
            else -> {
                navigateToRegisterEntry()
            }
        }
    }

    private fun navigateToRegisterStatus() {
        val intent = Intent(this, RegisterStatusActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegisterEntry() {
        val intent = Intent(this, RegisterEntryActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setKakaoLoginBtnClickListener() {
        binding.kakaoLoginBtn.setOnClickListener {
            handleKakaoLogin()
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            loginViewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Loading -> {
                        // 로딩 상태 처리 (프로그레스바 표시)
                    }

                    is LoginState.Success -> {
                        Toast.makeText(this@LoginEntryActivity, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
                        checkRole()
                    }

                    is LoginState.Error -> {
                        binding.loginErrorTextView.text = state.message
                    }

                    LoginState.Idle -> {}
                }
            }
        }
    }

    private fun handleKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.e(TAG, "카카오 계정 로그인 콜백")
            if (error != null) {
                Log.e(TAG, "카카오 계정으로 로그인 실패", error)
                loginViewModel.updateErrorMessage("카카오 로그인 실패")
            } else if (token != null) {
                Log.i(TAG, "카카오 계정으로 로그인 성공 ${token.accessToken}")
                fetchUserInfo(token.accessToken)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    fetchUserInfo(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this@LoginEntryActivity, callback = callback)
        }
    }

    private fun fetchUserInfo(accessToken: String) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
                loginViewModel.updateErrorMessage("사용자 정보 요청 실패")
            } else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공: ${user.kakaoAccount?.profile?.nickname}, ${user.id}")

                loginViewModel.login(accessToken)
                Log.d(TAG, accessToken)
            }
        }
    }

    companion object {
        private const val TAG = "kakaoLogin"
    }
}
