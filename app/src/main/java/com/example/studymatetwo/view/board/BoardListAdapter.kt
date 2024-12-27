package com.example.studymatetwo.view.board

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studymatetwo.databinding.BoardListItemBinding
import com.example.studymatetwo.databinding.MentorListItemBinding
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.ChatRoomDto
import com.example.studymatetwo.dto.MentorDto
import com.example.studymatetwo.view.mentorSearchFragment.mentorListAdapter.MentorListAdapter

class BoardListAdapter() : RecyclerView.Adapter<BoardListAdapter.MyView>() {
    private var boardList:  List<BoardDto> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(item: BoardDto)
    }

    private var listener : BoardListAdapter.OnItemClickListener? = null

    fun setOnItemClickListener(listener : BoardListAdapter.OnItemClickListener) {
        this.listener = listener
    }

    inner class MyView(private val binding: BoardListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(boardModel: BoardDto) {
            binding.titleText.text = boardModel.title
            binding.dateText.text = boardModel.createdAt
            binding.nicknameText.text = boardModel.nickname
            binding.root.setOnClickListener {
                listener?.onItemClick(boardModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = BoardListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(boardList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<BoardDto>) {
        boardList = list
        notifyDataSetChanged()
    }
}