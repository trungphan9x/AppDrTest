package com.trung.applicationdoctor.ui.activity.detail

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.core.BaseViewModel
import com.trung.applicationdoctor.data.remote.response.ChannelDetail
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelDetailRoomRepository
import com.trung.applicationdoctor.ui.activity.detail.DetailActivity.Companion.ERROR
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
                    channelApiRepository.getChannelDetail(memberIdx = context.getUserMemberIdx().toString(), boardId = boardId).let { result->
                        result.data?.let {
                            when (result.data.code) {
                                "1000" -> {
                                    viewModelScope.launch (defaultDispatcher){
                                        insertDetailChannelToRoom(result.data)
                                    }
                                    //channelDetail.set(it)
                                    channelDetail.postValue(result.data)
                                }
                                else -> _uiEvent.postValue(UIEvent(ERROR, it.codeMsg))
                            }
                        }

                    }
                } else {
                    channelDetailRoomRepository.getChannelDetail(boardId).let {
                        if(it != null) {
                            //channelDetail.set(it)
                            channelDetail.postValue(it)

                        } else {
                            _uiEvent.postValue(UIEvent(ERROR, "You have no internet connection to open the page"))
                        }
                    }

                }

            }
        } catch (ex: Exception) {
            print("")

        } finally {
            print("")
        }


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