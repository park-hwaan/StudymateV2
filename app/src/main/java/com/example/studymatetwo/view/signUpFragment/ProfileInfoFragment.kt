package com.example.studymatetwo.view.signUpFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentProfileInfoBinding
import com.example.studymatetwo.view.SignUpActivity
import org.json.JSONObject

class ProfileInfoFragment : Fragment() {
    private lateinit var binding : FragmentProfileInfoBinding

    override fun onStop() {
        super.onStop()

        // 프래그먼트 데이터를 JSON 형식의 문자열로 생성
        val jsonData = JSONObject().apply {
            put("job", binding.editJob.text.toString())
            put("publicRelations", binding.editPublicRelations.text.toString())
            put("blogUrl", binding.editBlogUrl.text.toString())
            put("expertiseField",binding.editExpertiseField.text.toString())
        }.toString()

        // ProfileSetting 액티비티의 receiveData 함수 호출
        val mainActivity = activity as SignUpActivity
        mainActivity.receiveData(this, jsonData)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileInfoBinding.inflate(inflater, container, false)


        return binding.root
    }


}