package com.example.studymatetwo.view.mentorSearchFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentMajorPartBinding
import com.example.studymatetwo.databinding.FragmentQuestionContentBinding
import com.example.studymatetwo.view.MentorSearchActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
@AndroidEntryPoint
class QuestionContentFragment : Fragment() {
    private lateinit var binding : FragmentQuestionContentBinding
    override fun onStop() {
        super.onStop()

        val jsonData = JSONObject().apply {
            put("title", binding.editTitle.text.toString())
            put("content", binding.editContent.text.toString())
        }.toString()

        val mainActivity = activity as MentorSearchActivity
        mainActivity.receiveData(this, jsonData)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionContentBinding.inflate(inflater, container, false)

        return binding.root
    }

}