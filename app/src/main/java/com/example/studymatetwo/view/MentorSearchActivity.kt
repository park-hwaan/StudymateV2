package com.example.studymatetwo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.ActivityMainBinding
import com.example.studymatetwo.databinding.ActivityMentorSearchBinding
import com.example.studymatetwo.dto.SignUpDto
import com.example.studymatetwo.view.mentorSearchFragment.InterestFragment
import com.example.studymatetwo.view.signUpFragment.*
import com.example.studymatetwo.viewmodel.SignViewModel

class MentorSearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMentorSearchBinding
    private val viewModel: SignViewModel by viewModels()

    private val fragmentList : List<Fragment> = listOf(
        InterestFragment()
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
        binding = ActivityMentorSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.onBackPressedDispatcher.addCallback(this, callback)

        binding.progressBar.max = fragmentList.size
        binding.progressBar.progress = 1

        // 시작 프래그먼트 로드
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout, InterestFragment())
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
    }
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

}