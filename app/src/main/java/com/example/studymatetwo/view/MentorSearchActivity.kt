package com.example.studymatetwo.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.ActivityMentorSearchBinding
import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.dto.SignUpDto
import com.example.studymatetwo.view.mentorSearchFragment.QuestionContentFragment
import com.example.studymatetwo.view.mentorSearchFragment.QuestionInterestFragment
import com.example.studymatetwo.view.signUpFragment.*
import com.example.studymatetwo.viewmodel.MentorSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MentorSearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMentorSearchBinding
    private val viewModel: MentorSearchViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    private val fragmentList : List<Fragment> = listOf(
        QuestionInterestFragment(),
        QuestionContentFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMentorSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userToken = sharedPreferences.getString("userToken", "").toString()

        this.onBackPressedDispatcher.addCallback(this, callback)

        changeFragment(QuestionInterestFragment())

        binding.skipText.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // 뒤로가기 이미지 클릭 이벤트
        binding.backImg.setOnClickListener {
            if(viewModel.cursor.value!! > 1){
                supportFragmentManager.popBackStack()
                viewModel.previousCursor()
            } else finish()
        }

        binding.nextBtn.setOnClickListener {
            if (viewModel.cursor.value!! <= fragmentList.size) {
                if (viewModel.cursor.value == fragmentList.size) {
                    viewModel.postMenteeQuestion("Bearer $userToken",viewModel.questionData.value ?: MenteeQuestionDto())
                } else {
                    changeFragment(fragmentList[viewModel.cursor.value!!])
                    viewModel.nextCursor()
                }
            }
        }

        observeNextBtn()
        observeProgressBar()
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (viewModel.cursor.value!! > 1) {
                supportFragmentManager.popBackStack()
                viewModel.previousCursor()
            } else finish()
        }
    }

    fun receiveData(fragment: Fragment, data: String) {
        val jsonObject = JSONObject(data)
        val currentData = viewModel.questionData.value ?: MenteeQuestionDto()

        when (fragment) {
            is QuestionInterestFragment -> {
                currentData.specificField = jsonObject.optString("specify", "")
                currentData.interests = jsonObject.optString("interests","")
            }
            is QuestionContentFragment -> {
                currentData.title = jsonObject.optString("title", "")
                currentData.content = jsonObject.optString("content","")
            }
        }
        viewModel.updateQuestionData(currentData)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observeNextBtn(){
        viewModel.nextBtnText.observe(this, Observer {
            binding.nextBtn.text = it
        })
    }

    private fun observeProgressBar() {
        viewModel.progressBarValue.observe(this, Observer { progress ->
            binding.progressBar.progress = progress
        })
    }
}