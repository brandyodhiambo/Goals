package com.kanyiakinyidevelopers.goals.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

fun onBoardingDone(activity: Activity) {
    val sharedPref: SharedPreferences =
        activity.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putBoolean("Finished", true)
    editor.apply()
}