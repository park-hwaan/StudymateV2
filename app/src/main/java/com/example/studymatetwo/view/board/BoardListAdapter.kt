package com.example.studymatetwo.view.board

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studymatetwo.databinding.BoardListItemBinding
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.room.entity.BoardEntity

class BoardListAdapter() : RecyclerView.Adapter<BoardListAdapter.MyView>() {
    private var boardList:  List<BoardEntity> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(item: BoardEntity)
    }

    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class MyView(private val binding: BoardListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(boardModel: BoardEntity) {
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
    fun setList(list: List<BoardEntity>) {
        boardList = list
        notifyDataSetChanged()
    }
}