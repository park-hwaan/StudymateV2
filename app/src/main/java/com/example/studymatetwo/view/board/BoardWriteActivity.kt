package com.example.studymatetwo.view.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studymatetwo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)

    }
}