package com.example.studymatetwo.view.mainFragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.studymatetwo.R
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.databinding.FragmentMentorSearchBinding
import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.view.HomeActivity
import com.example.studymatetwo.viewmodel.MentorSearchViewModel
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MentorSearchFragment : Fragment() {
    lateinit var binding : FragmentMentorSearchBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val mentorSearchViewModel: MentorSearchViewModel by viewModels()
    private var selectedInterest: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorSearchBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userToken = sharedPreferences.getString("userToken", "").toString()

        binding.interestsSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.interests_array))

        binding.interestsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedInterest = handleInterestsItemSelected(position)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedInterest = "PROGRAMMING"
            }
        }

        binding.mentorSearchBtn.setOnClickListener {
            val questionDto = MenteeQuestionDto(binding.editTitle.text.toString(), binding.editContent.text.toString(), selectedInterest, binding.editSpecifyField.text.toString())
            mentorSearchViewModel.postMenteeQuestion("Bearer $userToken", questionDto)
        }

        postQuestionObserve()

        return  binding.root
    }
    private fun handleInterestsItemSelected(position: Int) : String {
        val interests = when (position) {
            1 -> "PROGRAMMING"
            2 -> "PROGRAMMING"
            3 -> "PROGRAMMING"
            4 -> "PROGRAMMING"
            5 -> "PROGRAMMING"
            else -> "PROGRAMMING"
        }
        return interests
    }

    private fun postQuestionObserve(){
        mentorSearchViewModel.postMenteeQuestionResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    Toast.makeText(requireContext(), "질문 성공!", Toast.LENGTH_SHORT).show()
                }
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), "입력값을 확인해주세요!", Toast.LENGTH_SHORT).show()
                }
                is ApiResponse.Loading -> {
                    Toast.makeText(requireContext(), "질문 보내는 중...", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}