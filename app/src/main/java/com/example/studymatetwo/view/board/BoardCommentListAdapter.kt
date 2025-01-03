package com.example.studymatetwo.view.board

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studymatetwo.databinding.CommentItemBinding
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto

class BoardCommentListAdapter() : RecyclerView.Adapter<BoardCommentListAdapter.MyView>() {
    private var commentList: List<CommentDto> = emptyList()

    inner class MyView(private val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commentDto: CommentDto) {
            binding.timeText.text = commentDto.createdAt
            binding.contentText.text = commentDto.content
            binding.nicknameText.text = commentDto.nickname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardCommentListAdapter.MyView {
        val view = CommentItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyView(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
       holder.bind(commentList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<CommentDto>) {
        commentList = list
        notifyDataSetChanged()
    }
}