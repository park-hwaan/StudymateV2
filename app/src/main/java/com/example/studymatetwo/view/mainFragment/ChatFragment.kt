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
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymatetwo.R
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.databinding.FragmentChatBinding
import com.example.studymatetwo.databinding.FragmentMypageBinding
import com.example.studymatetwo.dto.ChatRoomDto
import com.example.studymatetwo.view.ChatRoomActivity
import com.example.studymatetwo.view.HomeActivity
import com.example.studymatetwo.view.mainFragment.chatRoomListAdapter.ChatRoomListAdapter
import com.example.studymatetwo.view.mentorSearchFragment.mentorListAdapter.MentorListAdapter
import com.example.studymatetwo.viewmodel.ChatViewModel
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {
    lateinit var binding : FragmentChatBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var chatRoomListAdapter: ChatRoomListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userToken = sharedPreferences.getString("userToken", "").toString()

        chatRoomListAdapter  = ChatRoomListAdapter()
        binding.recycle.layoutManager = LinearLayoutManager(requireContext())
        binding.recycle.adapter = chatRoomListAdapter

        viewModel.getMentorList("Bearer $userToken")
        observerChatRoom()

       chatRoomListAdapter.setOnItemClickListener(object : ChatRoomListAdapter.OnItemClickListener{
           override fun onItemClick(item: ChatRoomDto) {
               val intent = Intent(requireContext(), ChatRoomActivity::class.java)
               val roomId = item.roomId
               intent.putExtra("roomId",roomId)
               startActivity(intent)
           }

       })

        return binding.root
    }

    private fun observerChatRoom(){
        viewModel.chatRoomList.observe(viewLifecycleOwner, Observer { response ->
            chatRoomListAdapter.setList(response)
        })
    }

}