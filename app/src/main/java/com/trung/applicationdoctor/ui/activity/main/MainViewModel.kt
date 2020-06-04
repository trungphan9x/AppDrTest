package com.trung.applicationdoctor.ui.activity.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor
import com.trung.applicationdoctor.base.BaseViewModel
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelListRoomRepository
import com.trung.applicationdoctor.extension.isNetworkConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val channelApiRepository: ChannelApiRepository,
                    private val channelCategoryRoomRepository: ChannelCategoryRoomRepository,
                    private val channelListRoomRepository: ChannelListRoomRepository) : BaseViewModel() {

    var allChannelCategory: LiveData<List<ChannelCategory>> = channelCategoryRoomRepository.allChannelCategory

    val searchLiveData = MutableLiveData<String?>()

    val isSearchDisplayed = ObservableBoolean(false)

    val clickedTab = ObservableField<String>()

    init {
        getChannelCategoryApi()
    }

    private fun getChannelCategoryApi() {
        try {
            viewModelScope.launch (Dispatchers.IO){
                if(ApplicationDoctor.context.isNetworkConnected){
                    channelApiRepository.getCategoryList().dataArray.let {
                        insertAllToChannelCategoryDb(it)
                    }
                } else {

                }
            }

        } catch (ex: Exception) {

        }
    }


    private fun insertAllToChannelCategoryDb(listChannelCategory: List<ChannelCategory>) {
        viewModelScope.launch (Dispatchers.IO){
            channelCategoryRoomRepository.insertAll(listChannelCategory)
        }
    }
}