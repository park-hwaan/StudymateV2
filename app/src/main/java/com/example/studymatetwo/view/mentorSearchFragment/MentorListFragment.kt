package com.example.studymatetwo.view.mentorSearchFragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentMentorListBinding
import com.example.studymatetwo.databinding.FragmentQuestionContentBinding
import com.example.studymatetwo.dto.MentorDto
import com.example.studymatetwo.view.HomeActivity
import com.example.studymatetwo.viewmodel.MentorSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MentorListFragment : Fragment() {
    private lateinit var binding : FragmentMentorListBinding
    private val viewModel: MentorSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorListBinding.inflate(inflater, container, false)

        val mentorListAdapter = MentorListAdapter()
        binding.recycle.layoutManager = LinearLayoutManager(requireContext())
        binding.recycle.adapter = mentorListAdapter

        val mentorList = arguments?.getParcelableArrayList<MentorDto>("mentorList")
        mentorList?.let {
            mentorListAdapter.setList(it)
        }

        mentorListAdapter.setOnItemClickListener(object : MentorListAdapter.OnItemClickListener {
            override fun onButtonClick(item: MentorDto) {
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
            }

        })

        return binding.root
    }
}
