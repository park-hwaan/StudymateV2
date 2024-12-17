package com.example.studymatetwo.view.mainFragment.chatRoomListAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studymatetwo.databinding.ChatRoomListItemBinding
import com.example.studymatetwo.databinding.MentorListItemBinding
import com.example.studymatetwo.dto.ChatRoomDto
import com.example.studymatetwo.dto.MentorDto
import com.example.studymatetwo.view.mentorSearchFragment.mentorListAdapter.MentorListAdapter

class ChatRoomListAdapter() : RecyclerView.Adapter<ChatRoomListAdapter.MyView>()  {
    private var chatRoomList: List<ChatRoomDto> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(item: ChatRoomDto)
    }
    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<ChatRoomDto>){
        chatRoomList=list
        notifyDataSetChanged()
    }

    inner class MyView(private val binding: ChatRoomListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ChatRoomDto){
            binding.name.text = item.name
            binding.root.setOnClickListener {
                listener?.onItemClick(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomListAdapter.MyView {
        val view = ChatRoomListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(chatRoomList[position])
    }

    override fun getItemCount(): Int {
        return chatRoomList.size
    }
}