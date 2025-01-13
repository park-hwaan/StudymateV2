package com.example.studymatetwo.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.ActivityHomeBinding
import com.example.studymatetwo.view.board.BoardFragment
import com.example.studymatetwo.view.chat.ChatFragment
import com.example.studymatetwo.view.myPage.MyPageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(BoardFragment())

        binding.bottomNav.setOnItemSelectedListener {
            handleBottomNavigation(it.itemId)
        }
    }

    private fun handleBottomNavigation(itemId: Int): Boolean {
        val fragment = when (itemId) {
            R.id.chat -> ChatFragment()
            R.id.board -> BoardFragment()
            R.id.myPage -> MyPageFragment()
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