package com.example.studymatetwo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.ActivityMentorSearchBinding
import com.example.studymatetwo.dto.SignUpDto
import com.example.studymatetwo.view.mentorSearchFragment.QuestionContentFragment
import com.example.studymatetwo.view.mentorSearchFragment.QuestionInterestFragment
import com.example.studymatetwo.viewmodel.MentorSearchViewModel
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MentorSearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMentorSearchBinding
    private val viewModel: MentorSearchViewModel by viewModels()

    private val fragmentList : List<Fragment> = listOf(
        QuestionInterestFragment(),
        QuestionContentFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMentorSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.onBackPressedDispatcher.addCallback(this, callback)

        binding.progressBar.max = fragmentList.size
        binding.progressBar.progress = 1

        changeFragment(QuestionInterestFragment())

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
                    //여기다 마지막 프래그먼트 일때 viewmodel.postquestion호출
                } else {
                    changeFragment(fragmentList[viewModel.cursor.value!!])
                    viewModel.nextCursor()
                    increaseProgress()
                }
            }
        }

        nextBtnObserve()
    }

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
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun nextBtnObserve(){
        viewModel.nextBtnText.observe(this, Observer {
            binding.nextBtn.text = it
        })
    }
}