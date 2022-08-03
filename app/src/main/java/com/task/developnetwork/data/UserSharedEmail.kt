package com.task.developnetwork.data

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import javax.inject.Inject


class UserSharedEmail @Inject constructor(application: Application) {

    var sharedPreferences: SharedPreferences = application.getSharedPreferences("userEmail", MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()


    fun saveUserMail(email: String){
        editor.putString("email", email)
        editor.apply()

    }

    fun getUserMail():String{
        return sharedPreferences.getString("email", "").toString()
    }

}