package com.example.studymatetwo.view.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.studymatetwo.databinding.GetMessageItemBinding
import com.example.studymatetwo.databinding.SendMessageItemBinding
import com.example.studymatetwo.dto.MessageDto

class ChatMessageAdapter(private val nickname: String) : RecyclerView.Adapter<ChatMessageAdapter.MyView>() {
    private var messageList : MutableList<MessageDto> = mutableListOf()

    fun addMessage(message: MessageDto) {
        messageList.add(message)
        notifyItemInserted(messageList.size)
    }

    inner class MyView(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageDto) {
            Log.d("ChatMessageAdapter", "Binding message: ${item.message}, sender: ${item.sender}")
            if (binding is SendMessageItemBinding && item.sender == nickname) {
                // 내가 보낸 메시지 처리
                binding.message.text = item.message
            } else if (binding is GetMessageItemBinding) {
                // 상대방이 보낸 메시지 처리
                binding.sender.text = item.sender
                binding.message.text = item.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SEND_MESSAGE -> {
                val binding = SendMessageItemBinding.inflate(inflater, parent, false)
                MyView(binding)
            }
            VIEW_TYPE_GET_MESSAGE -> {
                val binding = GetMessageItemBinding.inflate(inflater, parent, false)
                MyView(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(messageList[position])
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val messageModel = messageList[position]
        return if (messageModel.sender == nickname) {
            VIEW_TYPE_SEND_MESSAGE
        } else {
            VIEW_TYPE_GET_MESSAGE
        }
    }

    companion object {
        private const val VIEW_TYPE_SEND_MESSAGE = 1
        private const val VIEW_TYPE_GET_MESSAGE = 2
    }
}