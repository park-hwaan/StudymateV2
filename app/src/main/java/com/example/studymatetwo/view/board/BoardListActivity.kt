package com.example.studymatetwo.view.board

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymatetwo.databinding.ActivityBoardListBinding
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.view.chat.ChatMessageAdapter
import com.example.studymatetwo.viewmodel.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardListBinding
    private val viewModel: BoardViewModel by viewModels()
    private lateinit var boardCategory: String
    private lateinit var userToken : String
    private lateinit var boardListAdapter: BoardListAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        userToken = sharedPreferences.getString("userToken", "") ?: ""

        boardCategory = intent.getStringExtra("boardCategory").toString()

        boardListAdapter = BoardListAdapter()
        binding.recycle.layoutManager = LinearLayoutManager(this)
        binding.recycle.adapter = boardListAdapter

        viewModel.getBoardList("Bearer $userToken")

        observerBoardList(boardCategory)
    }

    private fun observerBoardList(category: String){
        viewModel.boardList.observe(this, Observer { response ->
            val filterBoardList =response?.filter { it.category == category } ?: emptyList()
            boardListAdapter.setList(filterBoardList)
        })
    }
}