package com.anlovemi.dynamiclanguage

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import java.util.*

class LanContextWrapper(ctx: Context) : ContextWrapper(ctx) {
    companion object {

        const val SP_NAME = "name"
        const val LANGUAGE = "language"


        const val LANG_CN = "cn"
        const val LANG_EN = "en"

        fun wrap(context: Context): ContextWrapper {
            val newLocale: Locale
            val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val lanTag = sharedPreferences.getString(LANGUAGE, "def")
            when (lanTag) {
                "def"//没有手东设置过对应的语言则默认读取手机系统的语言
                -> {
                    val locale = Locale.getDefault().language
                    var langFlag = ""
                    if (TextUtils.isEmpty(locale)) {
                        langFlag = LANG_CN
                        newLocale = Locale.CHINESE
                    } else if (locale.contains("en")) {
                        langFlag = LANG_EN
                        newLocale = Locale.ENGLISH
                    } else if (locale.startsWith("zh")) {
                        langFlag = LANG_CN
                        newLocale = Locale.SIMPLIFIED_CHINESE
                    } else {
                        newLocale = Locale.CHINESE
                    }
                    sharedPreferences.edit().putString(LANGUAGE, langFlag).apply()
                }
                LANG_EN//设置为英语
                -> newLocale = Locale.ENGLISH
                LANG_CN -> newLocale = Locale.SIMPLIFIED_CHINESE
                else//默认为汉语
                -> newLocale = Locale.SIMPLIFIED_CHINESE
            }
            return ContextWrapper(getLanContext(context, newLocale))
        }

        /**
         * 初始化Context
         * @param context
         * @param pNewLocale
         * @return
         */
        private fun getLanContext(context: Context, pNewLocale: Locale): Context {
            val res = context.applicationContext.resources//1、获取Resources
            val configuration = res.configuration//2、获取Configuration
            //3、设置Locale并初始化Context
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(pNewLocale)
                val localeList = LocaleList(pNewLocale)
                LocaleList.setDefault(localeList)
                configuration.locales = localeList
                context.createConfigurationContext(configuration)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                configuration.setLocale(pNewLocale)
                context.createConfigurationContext(configuration)
            } else {
                context
            }
        }


        /**
         * 获取手机设置的语言国家
         * @param context
         * @return
         */
        fun getCountry(context: Context): String {
            val country: String
            val resources = context.applicationContext.resources
            //在7.0以上和7.0一下获取国家的方式有点不一样
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                country = resources.configuration.locales.get(0).country
            } else {
                country = resources.configuration.locale.country
            }
            return country
        }
    }
}