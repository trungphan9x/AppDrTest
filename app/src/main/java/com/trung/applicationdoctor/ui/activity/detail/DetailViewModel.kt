package com.trung.applicationdoctor.ui.activity.detail

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.core.BaseViewModel
import com.trung.applicationdoctor.data.enum.Status
import com.trung.applicationdoctor.data.remote.response.ChannelDetail
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelDetailRoomRepository
import com.trung.applicationdoctor.util.ERROR_EVENT
import com.trung.applicationdoctor.util.extension.isNetworkConnected
import com.trung.applicationdoctor.util.UIEvent
import com.trung.applicationdoctor.util.extension.getUserMemberIdx
import kotlinx.coroutines.*

class DetailViewModel(private val defaultDispatcher: CoroutineDispatcher,
                      private val channelDetailRoomRepository: ChannelDetailRoomRepository,
                      private val channelApiRepository: ChannelApiRepository) : BaseViewModel() {
    //var channelDetail: LiveData<ChannelDetailEntity?> = channelDetailRoomRepository.getChannelDetailLiveData()
    var detailView: DetailActivity? = null
    //val channelDetail = ObservableField<ChannelDetail>()

    val channelDetail: MutableLiveData<ChannelDetail?> = MutableLiveData<ChannelDetail?>()
    //val channelDetail: LiveData<ChannelDetail?> get() = _channelDetail


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
     * else showing the dialog that "You have no internet connection to open the page"
     * @param boardId : pass the parameter to the API to query its detail info
     */
    fun getDetailChannel(boardId: String) {
        try {
            viewModelScope.launch (defaultDispatcher) {
                if(context.isNetworkConnected) {
                    channelApiRepository.getChannelDetail(memberIdx = context.getUserMemberIdx().toString(), boardId = boardId).let { baseApiResult ->
                        when (baseApiResult.status) {
                            Status.SUCCESS -> {
                                baseApiResult.data?.let { result ->
                                    when (result.code) {
                                        "1000" -> {
                                            insertDetailChannelToRoom(result)
                                            //channelDetail.set(it)
                                            channelDetail.postValue(result)
                                        }
                                        else -> _uiEvent.postValue(UIEvent(ERROR_EVENT, result.codeMsg))
                                    }
                                }
                            }

                            Status.ERROR -> {
                                _uiEvent.postValue(UIEvent(ERROR_EVENT,baseApiResult.message))
                            }
                            Status.LOADING -> { }
                        }
                    }
                } else {
                    channelDetailRoomRepository.getChannelDetail(boardId).let {
                        if(it != null) {
                            //channelDetail.set(it)
                            channelDetail.postValue(it)
                        } else {
                            _uiEvent.postValue(UIEvent(ERROR_EVENT, "You have no internet connection to open the page"))
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            _uiEvent.postValue(UIEvent(ERROR_EVENT,ex.message))
        } finally { }


    }

    /**
     * Function to insert the return data from API to ROOM
     */
    private fun insertDetailChannelToRoom(channelDetail: ChannelDetail, callbackForTest: (() -> Unit)? = null) {
        viewModelScope.launch (defaultDispatcher) {
            channelDetailRoomRepository.insert(channelDetail)
        }
    }
}