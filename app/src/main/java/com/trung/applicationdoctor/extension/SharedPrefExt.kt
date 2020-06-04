package com.trung.applicationdoctor.extension

import android.content.Context

const val PREF_NAME = "shared_app_doctor"
const val PREF_EMAIL = "shared_email"
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
    return pref.getString(PREF_EMAIL, null)
}