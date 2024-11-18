package com.example.studymatetwo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.ActivityHomeBinding
import com.example.studymatetwo.view.mainFragment.BoardFragment
import com.example.studymatetwo.view.mainFragment.ChatFragment
import com.example.studymatetwo.view.mainFragment.MentorSearchFragment
import com.example.studymatetwo.view.mainFragment.MypageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(MentorSearchFragment())

        binding.bottomNav.setOnItemSelectedListener {
            handleBottomNavigation(it.itemId)
        }

    }

    private fun handleBottomNavigation(itemId: Int): Boolean {
        val fragment = when (itemId) {
            R.id.searchMentor -> MentorSearchFragment()
            R.id.chat -> ChatFragment() // 각 메뉴에 맞는 프래그먼트로 수정
            R.id.board -> BoardFragment()
            R.id.myPage -> MypageFragment()
            else -> null
        }
        return if (fragment != null) {
            loadFragment(fragment)
            true
        } else {
            false
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}