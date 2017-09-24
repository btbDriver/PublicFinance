package com.drive.finance.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by aaron on 2017/9/23.
 */
class LoginUtil(context: Context) {
    lateinit var sharedPreferences: SharedPreferences
    init {
        sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE)
    }

    fun setUid(uid: String) {
        sharedPreferences.edit().putString("uid", uid).commit()
    }

    fun getUid() : String {
        return sharedPreferences.getString("uid", "")
    }
}