package com.example.studymatetwo.view.mentorSearchFragment.mentorListAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studymatetwo.databinding.MentorListItemBinding
import com.example.studymatetwo.dto.MentorDto

class MentorListAdapter() : RecyclerView.Adapter<MentorListAdapter.MyView>() {
    private var mentorList: List<MentorDto> = emptyList()

    interface OnItemClickListener {
        fun onButtonClick(item: MentorDto)
    }
    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<MentorDto>){
        mentorList=list
        notifyDataSetChanged()
    }

    inner class MyView(private val binding: MentorListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MentorDto){
            binding.name.text = item.name

            binding.matchingBtn.setOnClickListener {
                listener?.onButtonClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = MentorListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(mentorList[position])
    }

    override fun getItemCount(): Int {
        return mentorList.size
    }


}