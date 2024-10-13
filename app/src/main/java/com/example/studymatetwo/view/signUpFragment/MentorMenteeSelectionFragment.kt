package com.example.studymatetwo.view.signUpFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentMentorMenteeSelectionBinding
import com.example.studymatetwo.view.SignUpActivity
import org.json.JSONObject

class MentorMenteeSelectionFragment : Fragment() {
    private lateinit var binding :  FragmentMentorMenteeSelectionBinding

    override fun onStop() {
        super.onStop()
        val mainActivity = activity as SignUpActivity
        val partValue = if(binding.menteeBtn.isSelected) {
            binding.menteeBtn.text.toString()
        } else {
            binding.mentorBtn.text.toString()
        }
        val jsonData = JSONObject().apply {
            put("part", partValue)
        }.toString()
        mainActivity.receiveData(this, jsonData)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorMenteeSelectionBinding.inflate(inflater, container, false)

        binding.mentorBtn.setOnClickListener {
            it.isSelected = !(it.isSelected)
            binding.menteeBtn.isSelected = false
        }

        binding.menteeBtn.setOnClickListener {
            it.isSelected = !(it.isSelected)
            binding.mentorBtn.isSelected = false
        }

        return binding.root
    }
}