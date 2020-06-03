package com.trung.applicationdoctor.ui.activity.main

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor
import com.trung.applicationdoctor.base.BaseViewModel
import com.trung.applicationdoctor.data.db.entity.ChannelCategoryEntity
import com.trung.applicationdoctor.data.db.entity.ChannelListEntity
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelListRoomRepository
import com.trung.applicationdoctor.extension.isNetworkConnected
import com.trung.applicationdoctor.ui.activity.main.MainActivity.Companion.CLICK_SEARCH
import com.trung.applicationdoctor.util.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val channelApiRepository: ChannelApiRepository,
                    private val channelCategoryRoomRepository: ChannelCategoryRoomRepository,
                    private val channelListRoomRepository: ChannelListRoomRepository) : BaseViewModel() {

    var allChannelList: LiveData<List<ChannelListEntity>> = channelListRoomRepository.allChannelList

    var allChannelCategory: LiveData<List<ChannelCategoryEntity>> = channelCategoryRoomRepository.allChannelCategory


    val searchLiveData = MutableLiveData<String?>()

    val isSearchDisplayed = ObservableBoolean(false)

    init {
        getChannelCategoryApi()
        getChannelListApi()
    }

    private fun getChannelCategoryApi() {
        try {
            viewModelScope.launch (Dispatchers.IO){
                if(ApplicationDoctor.context.isNetworkConnected()){
                    channelApiRepository.getCategoryList().dataArray.let {
                        insertAllToChannelCategoryDb(it)
                    }
                } else {

                }

            }

        } catch (ex: Exception) {

        }
    }

    private fun getChannelListApi() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                if(ApplicationDoctor.context.isNetworkConnected()){
                    channelApiRepository.getChannelList(pageNum = 1, memberId = "hehee").let {
                        insertAllToChannelListDb(it.dataArray)
                    }
                } else {

                }

            } catch (ex: Exception) {

            }
        }
    }

    private fun insertAllToChannelCategoryDb(listChannelCategory: List<ChannelCategory>) {
        viewModelScope.launch (Dispatchers.IO){
            channelCategoryRoomRepository.insertAll(listChannelCategory)
        }
    }

    private fun insertAllToChannelListDb(listChannelList: List<ChannelList>) {
        viewModelScope.launch (Dispatchers.IO){
            channelListRoomRepository.insertAll(listChannelList)
        }
    }

    fun clickSearch(view: View) {
        _uiEvent.postValue(UIEvent(CLICK_SEARCH))
    }
}