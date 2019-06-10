package com.anlovemi.dynamiclanguage

import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        chinese_btn.setOnClickListener {
            val sharedPreferences = getSharedPreferences(LanContextWrapper.SP_NAME, Context.MODE_PRIVATE)
            sharedPreferences.edit().putString(LanContextWrapper.LANGUAGE, LanContextWrapper.LANG_CN).apply()
            rebot()
        }
        english_btn.setOnClickListener {
            val sharedPreferences = getSharedPreferences(LanContextWrapper.SP_NAME, Context.MODE_PRIVATE)
            sharedPreferences.edit().putString(LanContextWrapper.LANGUAGE, LanContextWrapper.LANG_EN).apply()
            rebot()
        }
    }
}
