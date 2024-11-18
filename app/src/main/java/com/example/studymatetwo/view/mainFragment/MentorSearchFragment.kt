package com.example.studymatetwo.view.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentMentorSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MentorSearchFragment : Fragment() {
    lateinit var binding : FragmentMentorSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorSearchBinding.inflate(inflater, container, false)

        return  binding.root
    }

}