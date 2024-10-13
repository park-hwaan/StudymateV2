package com.example.studymatetwo.view.signUpFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentEmailBinding
import com.example.studymatetwo.databinding.FragmentFinishBinding

class FinishFragment : Fragment() {
    private lateinit var binding : FragmentFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinishBinding.inflate(inflater, container, false)

        return binding.root
    }


}