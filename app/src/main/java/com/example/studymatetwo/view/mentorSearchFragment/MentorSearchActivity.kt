package com.example.studymatetwo.view.mentorSearchFragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.studymatetwo.R
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.databinding.ActivityMentorSearchBinding
import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.util.IntroDialogUtil
import com.example.studymatetwo.view.HomeActivity
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
        QuestionContentFragment(),
        MentorListFragment()
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
            finish()
        }

        binding.backImg.setOnClickListener {
            if(viewModel.cursor.value!! > 1){
                supportFragmentManager.popBackStack()
                viewModel.previousCursor()
            } else finish()
        }

        binding.nextBtn.setOnClickListener {
            if (viewModel.cursor.value!! <= fragmentList.size) {
                if (viewModel.cursor.value == 3) {
                    IntroDialogUtil.showIntroDialog(this)
                    viewModel.postMenteeQuestion("Bearer $userToken", viewModel.questionData.value ?: MenteeQuestionDto())
                    observerMentorList()
                    observerMentor()
                    Log.d("MentorSearchActivity", "Cursor after increment: ${viewModel.cursor.value}")
                }
                else {
                    changeFragment(fragmentList[viewModel.cursor.value!!])
                    viewModel.nextCursor()
                    Log.d("MentorSearchActivity", "Cursor after increment: ${viewModel.cursor.value}")
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
            .addToBackStack(fragment::class.java.simpleName)
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
            binding.currentStateText.text = progress.toString()
        })
    }

    private fun observerMentorList(){
        sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userToken = sharedPreferences.getString("userToken", "").toString()

        viewModel.postMenteeQuestionResult.observe(this, Observer { response ->
            when (response) {
                is ApiResponse.Loading -> { }
                is ApiResponse.Success -> {
                    val questionId = response.data!!.id
                    viewModel.getMentorList("Bearer $userToken", questionId)
                }
                is ApiResponse.Error -> {
                    Toast.makeText(this, "입력 내용을 확인해주세요!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observerMentor(){
        viewModel.mentorList.observe(this, Observer {
            it?.let{
                val existingFragment = supportFragmentManager.findFragmentByTag(MentorListFragment::class.java.simpleName)
               if(existingFragment == null){
                   val mentorListFragment = MentorListFragment().apply {
                       arguments = Bundle().apply {
                           putParcelableArrayList("mentorList", ArrayList(it))
                       }
                   }
                   changeFragment(mentorListFragment)
               }
            }
        })
    }

}