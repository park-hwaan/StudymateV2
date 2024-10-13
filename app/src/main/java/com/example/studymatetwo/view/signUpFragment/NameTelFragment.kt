package com.example.studymatetwo.view.signUpFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentNameTelBinding
import com.example.studymatetwo.view.SignUpActivity
import org.json.JSONObject

class NameTelFragment : Fragment() {
    private lateinit var binding : FragmentNameTelBinding
    override fun onStop() {
        super.onStop()
        val mainActivity = activity as SignUpActivity
        val jsonData = JSONObject().apply {
            put("name", binding.editName.text.toString())
            put("tel",binding.editTel.text.toString())
        }.toString()
        mainActivity.receiveData(this, jsonData)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNameTelBinding.inflate(inflater, container, false)
        return binding.root
    }

}