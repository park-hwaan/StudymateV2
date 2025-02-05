package com.example.studymatetwo.view.board

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
import com.example.studymatetwo.databinding.FragmentBoardBinding
import com.example.studymatetwo.room.entity.BoardEntity
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
    private lateinit var searchHandler: SearchHandler
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

        binding.writeButton.setOnClickListener {
            val intent = Intent(requireContext(), BoardWriteActivity::class.java)
            startActivity(intent)
        }

        boardListAdapter.setOnItemClickListener(object : BoardListAdapter.OnItemClickListener{
            override fun onItemClick(item: BoardEntity) {
                val intent = Intent(requireContext(), BoardInsideActivity::class.java)
                val boardId = item.post_id
                intent.putExtra("boardId",boardId)
                startActivity(intent)
            }
        })

        viewModel.getBoardList("Bearer $userToken")

        observeLocalBoardList()

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 ->filterBoardList("FREE")
                    1 -> filterBoardList("QUESTION")
                    2 -> filterBoardList("STUDY")
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        searchHandler = SearchHandler(viewModel, userToken, boardListAdapter)
        searchHandler.initSearchView(binding.searchView)

//        observerBoardSearchList()
        onRefresh()
        observerErrorState()

        return binding.root
    }
    private fun observeLocalBoardList() {
        viewModel.localBoardList.observe(viewLifecycleOwner, Observer { boardList ->
            val category = when (binding.tabLayout.selectedTabPosition) {
                0 -> "FREE"
                1 -> "QUESTION"
                2 -> "STUDY"
                else -> "FREE"
            }
            filterBoardList(category, boardList)
        })
    }

    // ✅ 선택한 카테고리에 맞게 데이터 필터링
    private fun filterBoardList(category: String, boardList: List<BoardEntity>? = viewModel.localBoardList.value) {
        Log.d("BoardFragment", viewModel.localBoardList.value.toString())
        val filteredList = boardList?.filter { it.category == category } ?: emptyList()
        boardListAdapter.setList(filteredList)
    }

    // ✅ 새로고침 시 Room 데이터 가져오기
    private fun onRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            filterBoardList(when (binding.tabLayout.selectedTabPosition) {
                0 -> "FREE"
                1 -> "QUESTION"
                2 -> "STUDY"
                else -> "FREE"
            })
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

//    private fun observerBoardSearchList(){
//        viewModel.boardSearchList.observe(viewLifecycleOwner, Observer { response ->
//            boardListAdapter.setList(response)
//        })
//    }

    private fun observerErrorState(){
        viewModel.errorState.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

}