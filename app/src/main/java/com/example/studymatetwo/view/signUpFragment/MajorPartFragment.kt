package com.example.studymatetwo.view.signUpFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.FragmentMajorPartBinding
import com.example.studymatetwo.view.SignUpActivity
import org.json.JSONObject

class MajorPartFragment : Fragment() {
    private lateinit var binding : FragmentMajorPartBinding

    private lateinit var majorList: List<Button>
    private val koreanToEnglishMap = mapOf(
        "웹/앱" to "MATH",
        "서버/네트워크" to "MATH",
        "AI/IOT" to "MATH",
        "데이터개발" to "MATH",
        "보안" to "MATH",
    )

    override fun onStop() {
        super.onStop()

        val selectedButtonText = when {
            binding.majorBtn1.isSelected -> translateToEnglish(binding.majorBtn1.text.toString())
            binding.majorBtn2.isSelected -> translateToEnglish(binding.majorBtn2.text.toString())
            binding.majorBtn3.isSelected -> translateToEnglish(binding.majorBtn3.text.toString())
            binding.majorBtn4.isSelected -> translateToEnglish(binding.majorBtn4.text.toString())
            binding.majorBtn5.isSelected -> translateToEnglish(binding.majorBtn5.text.toString())
            else -> ""
        }

        // JSON 객체를 생성하고 키 "interests"에 해당 버튼의 텍스트를 넣어줍니다.
        val json = JSONObject().apply {
            put("interests", selectedButtonText)
        }

        // ProfileSetting 액티비티의 receiveData 함수 호출
        val mainActivity = activity as SignUpActivity
        mainActivity.receiveData(this, json.toString())
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMajorPartBinding.inflate(inflater, container, false)

        majorList = listOf(
            binding.majorBtn1,
            binding.majorBtn2,
            binding.majorBtn3,
            binding.majorBtn4,
            binding.majorBtn5
        )

        majorList.forEach { Button ->
            val remainList = majorList.filter { it != Button }

            Button.setOnClickListener {
                it.isSelected = !(it.isSelected)
                remainList.forEach { it.isSelected = false }
            }
        }

        return binding.root
    }

    private fun translateToEnglish(koreanText: String): String {
        return koreanToEnglishMap[koreanText] ?: koreanText
    }
}