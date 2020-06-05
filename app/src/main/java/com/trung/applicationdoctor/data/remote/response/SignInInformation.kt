package com.trung.applicationdoctor.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInInformation (
    @SerializedName("code") val code: String,
    @SerializedName("code_msg") val codeMsg: String,
    @SerializedName("member_idx") val memberIdx: String,
    @SerializedName("member_id") val memberId: String,
    @SerializedName("member_name") val memberName: String
)