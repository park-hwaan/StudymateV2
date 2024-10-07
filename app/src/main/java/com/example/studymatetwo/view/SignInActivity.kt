package com.example.studymatetwo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.databinding.ActivitySignInBinding
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.viewmodel.SignViewModel

class SignInActivity : AppCompatActivity() {
    lateinit var binding :ActivitySignInBinding
    private val signViewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //로그인
        binding.loginBtn.setOnClickListener {
            val signInDto = SignInDto(binding.editEmail.text.toString(), binding.editPassword.text.toString())
            signViewModel.postSignIn(signInDto)
        }

        //observe
        signInObserve()





    }
    //로그인 옵저브 함수
    private fun signInObserve(){
        signViewModel.signInResult.observe(this, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    // 로그인 성공 처리
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    // 다음 액티비티로 이동 등
                }
                is ApiResponse.Error -> {
                    // 로그인 실패 처리
                    Toast.makeText(this, "로그인 실패: ${response.message}", Toast.LENGTH_SHORT).show()
                }
                is ApiResponse.Loading -> {
                    // 로딩 처리
                    Toast.makeText(this, "로그인 중...", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}