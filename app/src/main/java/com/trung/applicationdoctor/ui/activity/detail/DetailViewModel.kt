package com.trung.applicationdoctor.ui.activity.detail

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.core.BaseViewModel
import com.trung.applicationdoctor.data.remote.response.ChannelDetail
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelDetailRoomRepository
import com.trung.applicationdoctor.ui.activity.detail.DetailActivity.Companion.ERROR
import com.trung.applicationdoctor.util.extension.getUserEmail
import com.trung.applicationdoctor.util.extension.isNetworkConnected
import com.trung.applicationdoctor.ui.activity.detail.DetailActivity.Companion.NO_INTERNET_AND_NO_DATA_IN_ROOM
import com.trung.applicationdoctor.util.UIEvent
import com.trung.applicationdoctor.util.extension.getUserMemberIdx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val channelDetailRoomRepository: ChannelDetailRoomRepository,
                        private val channelApiRepository: ChannelApiRepository) : BaseViewModel() {
    //var channelDetail: LiveData<ChannelDetailEntity?> = channelDetailRoomRepository.getChannelDetailLiveData()
    var detailView: DetailActivity? = null
    val channelDetail = ObservableField<ChannelDetail>()

    /**
     * Event click on back button
     */
    fun onClickBack(view: View) {
        detailView?.onBackPressed()
    }

    /**
     * Event click on bookmark button
     */
    fun onClickBookmark(view: View) {

    }

    /**
     * Before load DtailActivity, check if it has internet connection to call API and save the return data to ROOM
     * else if it has no internet, get data from ROOM to show to DetailActivity
     * else showing the dialog that "You have no internet connect to open the page"
     * @param boardId : pass the parameter to the API to query its detail info
     */
    fun getDetailChannel(boardId: String) {

        try {
            viewModelScope.launch (Dispatchers.IO) {
                if(context.isNetworkConnected) {
                    channelApiRepository.getChannelDetail(memberIdx = context.getUserMemberIdx().toString(), boardId = boardId).let {
                        when (it.code) {
                            "1000" -> {
                                insertDetailChannelToRoom(it)
                                channelDetail.set(it)
                            }
                            else -> _uiEvent.postValue(UIEvent(ERROR, it.codeMsg))
                        }

                    }
                } else {
                    channelDetailRoomRepository.getChannelDetail(boardId).let {
                        if(it != null) {
                            channelDetail.set(it)

                        } else {
                            _uiEvent.postValue(UIEvent(ERROR, "You have no internet connect to open the page"))
                        }
                    }
                }

            }
        } catch (ex: Exception) {

        } finally {

        }


    }

    /**
     * Function to insert the return data from API to ROOM
     */
    private fun insertDetailChannelToRoom(channelDetail: ChannelDetail) {
        viewModelScope.launch (Dispatchers.IO) {
            channelDetailRoomRepository.insert(channelDetail)
        }
    }
}