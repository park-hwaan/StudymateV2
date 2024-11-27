package com.example.studymatetwo.view.mainFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.studymatetwo.databinding.FragmentMypageBinding
import com.example.studymatetwo.viewmodel.MentorSearchViewModel
import com.example.studymatetwo.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment() {
    lateinit var binding : FragmentMypageBinding
    private val viewModel: SignViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userToken = sharedPreferences.getString("userToken", "").toString()

        viewModel.getMyInfo("Bearer $userToken")
        getMyInfo()

        return binding.root
    }
    private fun getMyInfo(){
        viewModel.myInfoData.observe(viewLifecycleOwner, Observer {
            Log.d("myInfo",it.data.toString())
            binding.reviewCount.text = it.data!!.reviewCount.toString()
            binding.matchingCount.text = it.data.matchingCount.toString()
            binding.nameText.text = it.data.name

        })
    }

}