package com.example.studymatetwo.view.board

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
        observerBoardList("FREE")

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

        observerBoardSearchList()
        initSearchView()

        onRefresh()

        observerErrorState()

        return binding.root
    }
    private fun observerBoardList(category: String){
        viewModel.boardList.observe(viewLifecycleOwner, Observer { response ->
            val filterBoardList =response?.filter { it.category == category } ?: emptyList()
            boardListAdapter.setList(filterBoardList)
        })
    }

    private fun onRefresh(){
        binding.swipeRefreshLayout.setOnRefreshListener {

            val category = when(binding.tabLayout.selectedTabPosition){
                0 -> "FREE"
                1 -> "QUESTION"
                2 -> "STUDY"
                else -> "FREE"
            }
            observerBoardList(category)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initSearchView() {
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { //검색을 완료하였을 경우 (키보드에 있는 '검색' 돋보기 버튼을 선택하였을 경우)
                query?.let { viewModel.getBoardSearch("Bearer $userToken",it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean { //검색어를 변경할 때마다 실행됨
                newText?.let { viewModel.getBoardSearch("Bearer $userToken",it) }
                return true
            }
        })
    }

    private fun observerBoardSearchList(){
        viewModel.boardSearchList.observe(viewLifecycleOwner, Observer { response ->
            boardListAdapter.setList(response)
        })
    }

    private fun observerErrorState(){
        viewModel.errorState.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

}