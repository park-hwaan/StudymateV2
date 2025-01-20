package com.example.studymatetwo.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.studymatetwo.databinding.ActivityMainBinding
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.view.mentorSearchFragment.MentorSearchActivity
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SignViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

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
            viewModel.postSignIn(signInDto)
        }

        signInObserve()
        observerErrorState()
    }

    private fun signInObserve(){
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        viewModel.signInResult.observe(this, Observer {
            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()

            val editor = sharedPreferences.edit()
            editor.putString("userToken", it.accessToken)
            editor.putString("refreshToken", it.refreshToken)
            editor.apply()

            lifecycleScope.launch {
                kotlinx.coroutines.delay(1000)
                val intent = Intent(this@MainActivity, MentorSearchActivity::class.java)
                startActivity(intent)

                binding.editEmail.text = null
                binding.editPassword.text = null
            }
        })
    }

    private fun observerErrorState(){
        viewModel.errorState.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}

