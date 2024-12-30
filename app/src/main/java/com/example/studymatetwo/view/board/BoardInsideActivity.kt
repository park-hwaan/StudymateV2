package com.example.studymatetwo.view.board

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.ActivityBoardInsideBinding
import com.example.studymatetwo.viewmodel.BoardViewModel
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardInsideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardInsideBinding
    private lateinit var boardId : String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userToken: String
    private val viewModel: BoardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userToken = sharedPreferences.getString("userToken", "").toString()

        boardId = intent.getStringExtra("boardId").toString()
        Log.d("BoardInsideActivity", boardId)

        viewModel.getBoardContent("Bearer $userToken", boardId)

        observeBoardContent()
    }

    private fun observeBoardContent(){
        viewModel.boardContent.observe(this, Observer {
            binding.nicknameText.text = it.data!!.nickname
            binding.dateText.text = it.data.createdAt
            binding.titleText.text = it.data.title
            binding.contentText.text = it.data.content
            binding.categoryText.text = it.data.category


        })
    }
}