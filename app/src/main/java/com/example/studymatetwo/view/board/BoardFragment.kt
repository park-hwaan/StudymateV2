package com.example.studymatetwo.view.board

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentBoardBinding
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.viewmodel.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardFragment : Fragment() {
    lateinit var binding : FragmentBoardBinding
    private val viewModel: BoardViewModel by viewModels()
    private lateinit var userToken : String
    private lateinit var boardListAdapter: BoardListAdapter
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userToken = sharedPreferences.getString("userToken", "") ?: ""


        boardListAdapter = BoardListAdapter()
        binding.recycle.layoutManager = LinearLayoutManager(requireContext())
        binding.recycle.adapter = boardListAdapter

        boardListAdapter.setOnItemClickListener(object : BoardListAdapter.OnItemClickListener{
            override fun onItemClick(item: BoardDto) {
                val intent = Intent(requireContext(), BoardListActivity::class.java)
                val boardId = item.post_id
                intent.putExtra("boardId",boardId)
                startActivity(intent)
            }

        })

        viewModel.getBoardList("Bearer $userToken")

        observerBoardList("STUDY")

        return binding.root
    }
    private fun observerBoardList(category: String){
        viewModel.boardList.observe(viewLifecycleOwner, Observer { response ->
            val filterBoardList =response?.filter { it.category == category } ?: emptyList()
            boardListAdapter.setList(filterBoardList)
        })
    }

}