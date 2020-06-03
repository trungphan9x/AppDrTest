package com.trung.applicationdoctor.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInInformation (
    @SerializedName("code") val code: String,
    @SerializedName("code_msg") val codeMsg: String,
    @SerializedName("focus_id") val focusID: String,
    @SerializedName("method") val method: String,
    @SerializedName("member_pw") val memberPw: String
)