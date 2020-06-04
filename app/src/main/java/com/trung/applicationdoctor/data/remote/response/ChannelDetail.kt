package com.trung.applicationdoctor.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChannelDetail (
    @SerializedName("code") val code: String? = null,
    @SerializedName("code_msg") val codeMsg: String? = null,
    @SerializedName("list_cnt") val listCnt: Long?,
    @SerializedName("board_idx") val boardIdx: String,
    @SerializedName("corp_idx") val corpIdx: String?,
    @SerializedName("contents_yn") val contentsYn: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("board_img") val boardImg: String?,
    @SerializedName("view_cnt") val viewCnt: String?,
    @SerializedName("contents") val contents: String?,
    @SerializedName("ins_date") val insDate: String?,
    @SerializedName("reply_cnt") val replyCnt: String?,
    @SerializedName("like_cnt") val likeCnt: String?,
    @SerializedName("my_like_yn") val myLikeYn: String?,
    @SerializedName("my_scrap_yn") val myScrapYn: String?,
    @SerializedName("category") val category: String?
)