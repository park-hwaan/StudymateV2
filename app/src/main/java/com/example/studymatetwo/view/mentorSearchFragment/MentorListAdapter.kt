package com.example.studymatetwo.view.mentorSearchFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studymatetwo.databinding.MentorListItemBinding
import com.example.studymatetwo.dto.MentorDto

class MentorListAdapter() : RecyclerView.Adapter<MentorListAdapter.MyView>() {
    private var mentorList: List<MentorDto> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<MentorDto>){
        mentorList=list
        notifyDataSetChanged()
    }

    inner class MyView(private val binding: MentorListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pos: Int){
            binding.name.text = mentorList[pos].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorListAdapter.MyView {
        val view = MentorListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MentorListAdapter.MyView, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return mentorList.size
    }


}