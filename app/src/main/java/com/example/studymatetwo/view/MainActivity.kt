package com.example.studymatetwo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.databinding.ActivityMainBinding
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.view.mentorSearchFragment.MentorSearchActivity
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

                    lifecycleScope.launch {
                        kotlinx.coroutines.delay(1000)
                        val intent = Intent(this@MainActivity, MentorSearchActivity::class.java)
                        startActivity(intent)

                        binding.editEmail.text = null
                        binding.editPassword.text = null
                    }
                }
                is ApiResponse.Error -> {
                    Toast.makeText(this, "로그인 실패: 비밀번호와 아이디를 확인해주세요", Toast.LENGTH_SHORT).show()
                }
                is ApiResponse.Loading -> {
                    Toast.makeText(this, "로그인 중...", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

