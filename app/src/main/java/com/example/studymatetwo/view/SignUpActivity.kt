package com.example.studymatetwo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.studymatetwo.R
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.databinding.ActivitySignUpBinding
import com.example.studymatetwo.dto.SignUpDto
import com.example.studymatetwo.view.signUpFragment.*
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignViewModel by viewModels()

    private val fragmentList : List<Fragment> = listOf(
        EmailFragment(),
        PasswordFragment(),
        NameTelFragment(),
        MentorMenteeSelectionFragment(),
        MajorPartFragment(),
        NicknameFragment(),
        ProfileInfoFragment(),
        FinishFragment()
    )

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (viewModel.cursor.value!! > 1) {
                supportFragmentManager.popBackStack()
                viewModel.previousCursor()
                decreaseProgress()
            } else finish()
        }
    }

    private fun increaseProgress(){
        binding.progressBar.progress += 1
    }

    private fun decreaseProgress(){
        binding.progressBar.progress -= 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        this.onBackPressedDispatcher.addCallback(this, callback)

        binding.progressBar.max = fragmentList.size
        binding.progressBar.progress = 1

        // 시작 프래그먼트 로드
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout, EmailFragment())
            .addToBackStack(null)
            .commit()

        // 뒤로가기 이미지 클릭 이벤트
        binding.backImg.setOnClickListener {
            if(viewModel.cursor.value!! > 1){
                supportFragmentManager.popBackStack()
                viewModel.previousCursor()
                decreaseProgress()
            } else finish()
        }

        binding.nextBtn.setOnClickListener {
            if (viewModel.cursor.value!! <= fragmentList.size) {
                if (viewModel.cursor.value == fragmentList.size) {
                    // 마지막 프래그먼트에서 회원가입 함수 호출
                    viewModel.postSignUp(viewModel.signUpData.value ?: SignUpDto())
                } else {
                    // 다음 프래그먼트로 이동
                    changeFragment(fragmentList[viewModel.cursor.value!!])
                    viewModel.nextCursor()
                    increaseProgress()
                }
            }
        }

        //회원가입 observe
        signUpObserve()
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun receiveData(fragment: Fragment, data: String) {
        val jsonObject = JSONObject(data)
        val currentData = viewModel.signUpData.value ?:SignUpDto()

        when (fragment) {
            is EmailFragment -> currentData!!.email = jsonObject.optString("email", "")
            is PasswordFragment -> currentData!!.password = jsonObject.optString("password", "")
            is NameTelFragment -> {
                currentData!!.name = jsonObject.optString("name", "")
                currentData.tel = jsonObject.optString("tel", "")
            }
            is MentorMenteeSelectionFragment -> currentData!!.part = jsonObject.optString("part", "")
            is MajorPartFragment -> currentData!!.interests = jsonObject.optString("interests", "")
            is NicknameFragment -> currentData!!.nickname = jsonObject.optString("nickname", "")
            is ProfileInfoFragment -> {
                currentData!!.blogUrl = jsonObject.optString("blogUrl", "")
                currentData.publicRelations = jsonObject.optString("publicRelations", "")
                currentData.job = jsonObject.optString("job", "")
                currentData.expertiseField = jsonObject.optString("expertiseField", "")
            }
        }
        viewModel.updateSignUpData(currentData)
    }

    //회원가입 observe
    private fun signUpObserve(){
        viewModel.signUpResult.observe(this) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    Toast.makeText(this, "회원가입 중...", Toast.LENGTH_SHORT).show()
                }
                is ApiResponse.Success -> {
                    Toast.makeText(this, "회원가입 성공!!", Toast.LENGTH_SHORT).show()
                    finishAffinity()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                is ApiResponse.Error -> {
                    Toast.makeText(this, "회원가입 실패!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}