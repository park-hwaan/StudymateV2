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

    object Prefs {
        private const val PREF_NAME = "app_prefs"
        private const val KEY_SHOW_DIALOG = "show_dialog"

        fun shouldShowDialog(context: Context): Boolean {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(KEY_SHOW_DIALOG, true) // 기본값은 true
        }

        fun setShowDialog(context: Context, show: Boolean) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean(KEY_SHOW_DIALOG, show).apply()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(BoardFragment())

        binding.bottomNav.setOnItemSelectedListener {
            handleBottomNavigation(it.itemId)
        }

        showIntroDialog(this)
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

    fun showIntroDialog(context: Context) {
        // SharedPreferences에서 다이얼로그 표시 여부 확인
        if (!Prefs.shouldShowDialog(context)) return

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_intro, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        // 체크박스와 확인 버튼 처리
        val checkBox = dialogView.findViewById<CheckBox>(R.id.checkbox_dont_show_again)
        val button = dialogView.findViewById<Button>(R.id.button_ok)

        button.setOnClickListener {
            // "다시 보지 않음" 체크 여부 저장
            if (checkBox.isChecked) {
                Prefs.setShowDialog(context, false)
            }
            dialog.dismiss()
        }

        dialog.show()
    }

}