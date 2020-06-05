package com.trung.applicationdoctor.util.extension

import android.content.Context

const val PREF_NAME = "shared_app_doctor"
const val PREF_EMAIL = "shared_email"
const val PREF_MEMBER_IDX = "shared_member_idx"
const val PREF_PW = "shared_pw"
const val FIRST_LAUNCH_APP = "first_launch_app"
/**
 * Extension function that can save email using Context
 */
fun Context.setUserEmail(email: String) {
    val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(PREF_EMAIL, email)
    editor.apply()
}

/**
 * Function to get user email using context
 */
fun Context.getUserEmail(): String? {
    val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    return pref.getString(PREF_EMAIL, "ttt@gmail.com")
}





/**
 * Extension function that can save password using Context
 */
fun Context.setUserPW(pw: String) {
    val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(PREF_PW, pw)
    editor.apply()
}

/**
 * Function to get user password using context
 */
fun Context.getUserPw(): String? {
    val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    return pref.getString(PREF_PW, "12122012gv!")
}



/**
 * Extension function that can save memberIdx using Context
 */
fun Context.setUserMemberIdx(memberIdx: String) {
    val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(PREF_MEMBER_IDX, memberIdx)
    editor.apply()
}

/**
 * Function to get user memberIdx using context
 */
fun Context.getUserMemberIdx(): String? {
    val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    return pref.getString(PREF_MEMBER_IDX, "143")
}


/**
 * Extension function that can save the var <hasFirstLaunchApp> using Context
 */
fun Context.setHasFirstLauchApp(hasFirstLaunchApp: Boolean) {
    val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putBoolean(FIRST_LAUNCH_APP, hasFirstLaunchApp)
    editor.apply()
}


/**
 * Function to check if app is run in the 1st time
 */
fun Context.hasFirstLaunchApp(): Boolean {
    val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    return pref.getBoolean(FIRST_LAUNCH_APP, true)
}

