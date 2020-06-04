package com.trung.applicationdoctor.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChannelCategory (
    @SerializedName("category_idx") val categoryIdx: String,
    @SerializedName("category_name") val categoryName: String,
    @SerializedName("ins_date") val insDate: String? = null
)