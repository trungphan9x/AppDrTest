package com.trung.applicationdoctor.data.remote

import com.google.gson.annotations.SerializedName

data class AppDoctorResponseBody<T> (
    @SerializedName("code") val code: String,
    @SerializedName("code_msg") val codeMsg: String?,
    @SerializedName("list_cnt") val listCnt: Int?,
    @SerializedName("page_num") val pageNum: String?,
    @SerializedName("total_page") val totalPage: Int?,
    @SerializedName("data_array") val dataArray: T
)