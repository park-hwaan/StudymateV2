package com.example.studymatetwo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.studymatetwo.R
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.databinding.ActivityMainBinding
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val signViewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //회원가입 화면으로 이동
        binding.signUpText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            val signInDto = SignInDto(binding.editEmail.text.toString(), binding.editPassword.text.toString())
            signViewModel.postSignIn(signInDto)
        }

        signInObserve()
    }

    private fun signInObserve(){
        signViewModel.signInResult.observe(this, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                is ApiResponse.Error -> {
                    Toast.makeText(this, "로그인 실패: 비밀번호와 아이디를 확인해주세요", Toast.LENGTH_SHORT).show()
                }
                is ApiResponse.Loading -> {
                    // 로딩 처리
                    Toast.makeText(this, "로그인 중...", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

