package com.example.studymatetwo.view.board

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymatetwo.databinding.FragmentBoardBinding
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.viewmodel.BoardViewModel
import com.google.android.material.tabs.TabLayout
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
                val intent = Intent(requireContext(), BoardInsideActivity::class.java)
                val boardId = item.post_id
                intent.putExtra("boardId",boardId)
                startActivity(intent)
            }

        })

        viewModel.getBoardList("Bearer $userToken")

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 ->observerBoardList("FREE")
                    1 -> observerBoardList("QUESTION")
                    2 -> observerBoardList("STUDY")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
               observerBoardList("FREE")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        return binding.root
    }
    private fun observerBoardList(category: String){
        viewModel.boardList.observe(viewLifecycleOwner, Observer { response ->
            val filterBoardList =response?.filter { it.category == category } ?: emptyList()
            boardListAdapter.setList(filterBoardList)
        })
    }

}