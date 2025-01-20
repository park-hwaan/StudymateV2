package com.example.studymatetwo.view.board

import androidx.appcompat.widget.SearchView
import com.example.studymatetwo.viewmodel.BoardViewModel

class SearchHandler(
    private val viewModel: BoardViewModel,
    private val userToken: String,
    private val adapter: BoardListAdapter
) {
    fun initSearchView(searchView: SearchView) {
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.getBoardSearch("Bearer $userToken", it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.getBoardSearch("Bearer $userToken", it) }
                return true
            }
        })
    }
}
