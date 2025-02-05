package com.example.studymatetwo.view.board

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymatetwo.databinding.ActivityBoardInsideBinding
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.viewmodel.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardInsideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardInsideBinding
    private lateinit var boardId : String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userToken: String
    private lateinit var commentContentDto: CommentContentDto
    private lateinit var commentListAdapter: BoardCommentListAdapter
    private val viewModel: BoardViewModel by viewModels()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
           finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userToken = sharedPreferences.getString("userToken", "").toString()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        binding.backImg.setOnClickListener {
            finish()
        }

        commentListAdapter = BoardCommentListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = commentListAdapter

        boardId = intent.getIntExtra("boardId", 0).toString()
        Log.d("BoardInsideActivity", boardId)

        binding.sendBtn.setOnClickListener {
            commentContentDto = CommentContentDto(binding.editComment.text.toString())
            viewModel.postBoardComment("Bearer $userToken", boardId, commentContentDto)
            binding.editComment.text = null
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getCommentList("Bearer $userToken", boardId)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.getBoardContent("Bearer $userToken", boardId)
        observeBoardContent()

        viewModel.getCommentList("Bearer $userToken", boardId)
        observerCommentList()
    }

    private fun observeBoardContent(){
        viewModel.boardContent.observe(this, Observer {
            val korCategory = boardCategory(it.category)

            binding.nicknameText.text = it.nickname
            binding.dateText.text = it.createdAt
            binding.titleText.text = it.title
            binding.contentText.text = it.content
            binding.categoryText.text = korCategory
            binding.heartAmountText.text = it.likeCount.toString()
            binding.commentAmountText.text = it.commentCount.toString()
        })
    }

   private fun boardCategory(category: String) : String{
       return when (category) {
           "STUDY" -> "스터디게시판"
           "QUESTION" -> "질문게시판"
           "FREE" -> "자유게시판"
           else -> ""
       }
   }

    private fun observerCommentList(){
        viewModel.commentList.observe(this, Observer {
            commentListAdapter.setList(it)
        })
    }
}