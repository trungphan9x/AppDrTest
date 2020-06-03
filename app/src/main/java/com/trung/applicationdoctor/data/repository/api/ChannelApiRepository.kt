package com.trung.applicationdoctor.data.repository.api

import com.trung.applicationdoctor.data.remote.AppDoctorResponseBody
import com.trung.applicationdoctor.data.remote.api.AppDoctorAPI
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.data.remote.response.ChannelDetail
import com.trung.applicationdoctor.data.remote.response.ChannelCategory

class ChannelApiRepository(private val appDoctorAPI: AppDoctorAPI) {

    suspend fun getCategoryList() : AppDoctorResponseBody<List<ChannelCategory>> {
        return appDoctorAPI.getCategoryList()
    }

    suspend fun getChannelList(memberId: String, pageNum: Int, categoryId: Int? = null) : AppDoctorResponseBody<List<ChannelList>> {
        return appDoctorAPI.getChannelList(memberId, pageNum, categoryId)
    }

    suspend fun getChannelDetail(memberId: String, boardId: Int) : ChannelDetail {
        return appDoctorAPI.getChannelDetail(memberId, boardId)
    }
}