package com.trung.applicationdoctor.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChannelList (
    @SerializedName("board_idx") val boardIdx: String,
    @SerializedName("board_type") val boardType: String?,
    @SerializedName("ins_date") val insDate: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("img_path") val imgPath: String?,
    @SerializedName("reply_cnt") val replyCnt: String?,
    @SerializedName("like_cnt") val likeCnt: String?,
    @SerializedName("my_like_yn") val myLikeYn: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("contents_yn") val contentsYn: String?
)