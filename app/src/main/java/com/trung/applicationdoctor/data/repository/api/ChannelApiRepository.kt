package com.trung.applicationdoctor.data.repository.api

import com.trung.applicationdoctor.core.BaseApiResult
import com.trung.applicationdoctor.core.BaseRepository
import com.trung.applicationdoctor.data.remote.AppDoctorResponseBody
import com.trung.applicationdoctor.data.remote.api.AppDoctorAPI
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.data.remote.response.ChannelDetail
import com.trung.applicationdoctor.data.remote.response.ChannelCategory

class ChannelApiRepository(private val appDoctorAPI: AppDoctorAPI) : BaseRepository() {

    suspend fun getCategoryList() : BaseApiResult<AppDoctorResponseBody<List<ChannelCategory>>> {
        return safeApi {
            appDoctorAPI.getCategoryList()
        }
    }

    suspend fun getChannelList(memberIdx: String, pageNum: Int, categoryId: String? = null) : BaseApiResult<AppDoctorResponseBody<List<ChannelList>>> {
        return safeApi {
            appDoctorAPI.getChannelList(memberIdx, pageNum, categoryId)
        }
    }

    suspend fun getChannelDetail(memberIdx: String, boardId: String) : BaseApiResult<ChannelDetail> {

        return safeApi {
            appDoctorAPI.getChannelDetail(memberIdx, boardId)
        }
    }
}