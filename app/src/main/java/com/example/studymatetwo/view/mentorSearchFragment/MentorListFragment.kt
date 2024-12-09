package com.example.studymatetwo.view.mentorSearchFragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.databinding.FragmentMentorListBinding
import com.example.studymatetwo.dto.MentorDto
import com.example.studymatetwo.view.HomeActivity
import com.example.studymatetwo.view.mentorSearchFragment.mentorListAdapter.MentorListAdapter
import com.example.studymatetwo.viewmodel.MentorSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MentorListFragment : Fragment() {
    private lateinit var binding : FragmentMentorListBinding
    private val viewModel: MentorSearchViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorListBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userToken = sharedPreferences.getString("userToken", "").toString()

        val mentorListAdapter = MentorListAdapter()
        binding.recycle.layoutManager = LinearLayoutManager(requireContext())
        binding.recycle.adapter = mentorListAdapter

        val mentorList = arguments?.getParcelableArrayList<MentorDto>("mentorList")
        mentorList?.let {
            mentorListAdapter.setList(it)
        }

        mentorListAdapter.setOnItemClickListener(object : MentorListAdapter.OnItemClickListener {
            override fun onButtonClick(item: MentorDto) {
                viewModel.postChatRoom("Bearer $userToken","parkhwan")
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
            }
        })

        observerChatRoom()

        return binding.root
    }

    private fun observerChatRoom(){
        viewModel.postChatRoomResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiResponse.Loading -> { }
                is ApiResponse.Success -> {
                    Toast.makeText(requireContext(), "채팅방이 생성되었습니다!", Toast.LENGTH_SHORT).show()
                }
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), "오류가 발생했습니다!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
