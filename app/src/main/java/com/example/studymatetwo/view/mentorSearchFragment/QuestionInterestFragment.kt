package com.example.studymatetwo.view.mentorSearchFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentEmailBinding
import com.example.studymatetwo.databinding.FragmentInterestBinding
import com.example.studymatetwo.view.MentorSearchActivity
import com.example.studymatetwo.view.SignUpActivity
import org.json.JSONObject


class QuestionInterestFragment : Fragment() {
    lateinit var binding : FragmentInterestBinding

    override fun onStop() {
        super.onStop()
        val selectedSpecifyButton = when{
            binding.aiTextView.isSelected -> "PROGRAMMING"
            binding.wepAppTextView.isSelected -> "PROGRAMMING"
            binding.dataTextView.isSelected -> "PROGRAMMING"
            binding.serverTextView.isSelected -> "PROGRAMMING"
            binding.securityTextView.isSelected -> "PROGRAMMING"
            else -> ""
        }

        val jsonData = JSONObject().apply {
            put("content", binding.editContent.text.toString())
            put("specify", selectedSpecifyButton)
        }.toString()

        val mainActivity = activity as MentorSearchActivity
        mainActivity.receiveData(this, jsonData)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterestBinding.inflate(inflater, container, false)

        return binding.root
    }
}