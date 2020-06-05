package com.trung.applicationdoctor.ui.activity.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor
import com.trung.applicationdoctor.core.BaseViewModel
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelListRoomRepository
import com.trung.applicationdoctor.util.extension.isNetworkConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val channelApiRepository: ChannelApiRepository,
                    private val channelCategoryRoomRepository: ChannelCategoryRoomRepository,
                    private val channelListRoomRepository: ChannelListRoomRepository) : BaseViewModel() {

    //the variable LiveData allChannelCategory which get all tab information from ROOM  automatically (when data of DB have any changes)
    var allChannelCategory: LiveData<List<ChannelCategory>> = channelCategoryRoomRepository.allChannelCategory

    val searchLiveData = MutableLiveData<String?>()

    val isSearchDisplayed = ObservableBoolean(false)

    val clickedTab = ObservableField<String>()

    init {
        getChannelCategoryApi()
    }

    /**
     * get tab names for TabLayout in activity_main.xml from API and save them to ROOM,
     * then the variable LiveData allChannelCategory which get all tab information from ROOM  automatically (when data of DB have any changes)
     * and update them directly to UI thanks to fun setTabTitle() in BindingAdapter.kt
     */
    private fun getChannelCategoryApi() {
        try {
            viewModelScope.launch (Dispatchers.IO){
                if(ApplicationDoctor.context.isNetworkConnected){
                    channelApiRepository.getCategoryList().let {
                        when (it.code) {
                            "1000" -> {
                                insertAllToChannelCategoryDb(it.dataArray)
                            }
                            "-1" -> { }
                            else -> { }
                        }
                    }
                } else {

                }
            }

        } catch (ex: Exception) {

        } finally {

        }
    }

    /**
     * Function to insert the return data from API to ROOM
     */
    private fun insertAllToChannelCategoryDb(listChannelCategory: List<ChannelCategory>) {
        viewModelScope.launch (Dispatchers.IO){
            channelCategoryRoomRepository.insertAll(listChannelCategory)
        }
    }
}