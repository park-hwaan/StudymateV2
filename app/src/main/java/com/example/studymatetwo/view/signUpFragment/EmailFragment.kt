package com.example.studymatetwo.view.signUpFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentEmailBinding
import com.example.studymatetwo.view.SignUpActivity
import org.json.JSONObject

class EmailFragment : Fragment() {
    lateinit var binding : FragmentEmailBinding

    override fun onStop() {
        super.onStop()

        // 프래그먼트 데이터를 JSON 형식의 문자열로 생성
        val jsonData = JSONObject().apply {
            put("email", binding.editEmail.text.toString())
        }.toString()

        // ProfileSetting 액티비티의 receiveData 함수 호출
        val mainActivity = activity as SignUpActivity
        mainActivity.receiveData(this, jsonData)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailBinding.inflate(inflater, container, false)


        return binding.root
    }

}