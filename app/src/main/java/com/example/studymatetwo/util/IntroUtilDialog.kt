package com.example.studymatetwo.util

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.studymatetwo.R

object IntroDialogUtil {

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

    fun showIntroDialog(context: Context) {
//        if (!Prefs.shouldShowDialog(context)) return

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_intro, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        val displayMetrics = Resources.getSystem().displayMetrics
        val dialogWidth = (displayMetrics.widthPixels * 0.4).toInt() // 가로 화면의 60%로 크기 설정
        val dialogHeight = (displayMetrics.heightPixels * 0.9).toInt() // 세로 화면의 90%로 크기 설정
        dialog.window?.setLayout(dialogWidth,dialogHeight)

        val checkBox = dialogView.findViewById<Button>(R.id.checkbox_dont_show_again)
        val button = dialogView.findViewById<Button>(R.id.button_ok)

        checkBox.setOnClickListener {
            Prefs.setShowDialog(context, false)
            dialog.dismiss()
        }

        button.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
