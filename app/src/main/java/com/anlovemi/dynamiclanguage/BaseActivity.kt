package com.anlovemi.dynamiclanguage

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    private var mLanTag: String? = null

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences(LanContextWrapper.SP_NAME, Context.MODE_PRIVATE)
        val lanTag = sharedPreferences.getString(LanContextWrapper.LANGUAGE, "def")
        if (mLanTag == null){
            mLanTag = lanTag
        }else{
            rebot()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        val context = newBase?.let { LanContextWrapper.wrap(it) }
        super.attachBaseContext(context)
    }

    fun rebot() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } else {
            recreate()
        }
    }

}