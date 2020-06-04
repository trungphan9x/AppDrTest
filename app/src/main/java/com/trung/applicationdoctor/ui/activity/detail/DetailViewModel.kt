package com.trung.applicationdoctor.ui.activity.detail

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.base.BaseViewModel
import com.trung.applicationdoctor.data.remote.response.ChannelDetail
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelDetailRoomRepository
import com.trung.applicationdoctor.extension.getUserEmail
import com.trung.applicationdoctor.extension.isNetworkConnected
import com.trung.applicationdoctor.ui.activity.detail.DetailActivity.Companion.NO_INTERNET_AND_NO_DATA_IN_ROOM
import com.trung.applicationdoctor.util.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val channelDetailRoomRepository: ChannelDetailRoomRepository,
                        private val channelApiRepository: ChannelApiRepository) : BaseViewModel() {
    //var channelDetail: LiveData<ChannelDetailEntity?> = channelDetailRoomRepository.getChannelDetailLiveData()
    var detailView: DetailActivity? = null
    val channelDetail = ObservableField<ChannelDetail>()

    fun onClickBack(view: View) {
        detailView?.onBackPressed()
    }

    fun onClickBookmark(view: View) {

    }

    fun getDetailChannel(boardId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            if(context.isNetworkConnected) {
                channelApiRepository.getChannelDetail(memberId = context.getUserEmail().toString(), boardId = boardId).let {
                    insertDetailChannelToRoom(it)
                    channelDetail.set(it)
                }
            } else {
                channelDetailRoomRepository.getChannelDetail(boardId).let {
                    if(it != null) {
                        channelDetail.set(it)

                    } else {
                        _uiEvent.postValue(UIEvent(NO_INTERNET_AND_NO_DATA_IN_ROOM))
                    }
                }
            }

        }
    }

    private fun insertDetailChannelToRoom(channelDetail: ChannelDetail) {
        viewModelScope.launch (Dispatchers.IO) {
            channelDetailRoomRepository.insert(channelDetail)
        }
    }
}