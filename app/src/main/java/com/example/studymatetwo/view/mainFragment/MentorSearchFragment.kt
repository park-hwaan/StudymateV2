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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymatetwo.R
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.databinding.FragmentMentorSearchBinding
import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.view.HomeActivity
import com.example.studymatetwo.view.mentorSearchFragment.MentorListAdapter
import com.example.studymatetwo.viewmodel.MentorSearchViewModel
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MentorSearchFragment : Fragment() {
    lateinit var binding : FragmentMentorSearchBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: MentorSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorSearchBinding.inflate(inflater, container, false)

        val mentorListAdapter = MentorListAdapter()
        binding.recycle.layoutManager = LinearLayoutManager(requireContext())
        binding.recycle.adapter = mentorListAdapter

        return  binding.root
    }

}