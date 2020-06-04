package com.trung.applicationdoctor.data.remote.api

import com.trung.applicationdoctor.data.remote.AppDoctorResponseBody
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.data.remote.response.ChannelDetail
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.remote.response.SignInInformation
import retrofit2.http.GET
import retrofit2.http.Query

interface AppDoctorAPI {

    @GET("/login_v_1_0_0/member_login")
    suspend fun signIn(
        @Query("member_id") memberId: String,
        @Query("member_pw") memberPw: String,
        @Query("gcm_key") gcmKey: Int,
        @Query("device_os") deviceOs: String
    ): SignInInformation

    @GET("/board_v_1_0_0/channel_category_list")
    suspend fun getCategoryList(): AppDoctorResponseBody<List<ChannelCategory>>

    @GET("/board_v_1_0_0/channel_list")
    suspend fun getChannelList(
        @Query("member_idx") memberId: String,
        @Query("page_num") pageNum: Int,
        @Query("category_idx") categoryId: String? = null
    ): AppDoctorResponseBody<List<ChannelList>>

    @GET("/board_v_1_0_0/channel_detail")
    suspend fun getChannelDetail(
        @Query("member_idx") memberId: String,
        @Query("board_idx") boardId: String
    ): ChannelDetail
}