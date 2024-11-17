package com.example.studymatetwo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studymatetwo.R
import com.example.studymatetwo.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}