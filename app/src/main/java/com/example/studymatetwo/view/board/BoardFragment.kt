package com.example.studymatetwo.view.board

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {
    lateinit var binding : FragmentBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)

        // 클릭 이벤트를 처리하는 공통 리스너
        val clickListener = View.OnClickListener { view ->
            val destination = when (view.id) {
                R.id.freeText -> "FREE"
                R.id.studyText -> "STUDY"
                R.id.quesText -> "QUESTION"
                else -> ""
            }
            navigateToActivity(destination)
        }

        // 클릭 이벤트를 설정할 TextView 목록
        val textViewList = listOf(binding.freeText, binding.quesText, binding.studyText)
        textViewList.forEach { it.setOnClickListener(clickListener) }

        return binding.root
    }

    private fun navigateToActivity(destination: String) {

        val intent = Intent(requireContext(), BoardListActivity::class.java).apply {
            putExtra("boardCategory", destination)
        }
        startActivity(intent)
    }
}