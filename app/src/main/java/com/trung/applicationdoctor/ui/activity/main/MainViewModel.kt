package com.trung.applicationdoctor.ui.activity.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor
import com.trung.applicationdoctor.core.BaseViewModel
import com.trung.applicationdoctor.data.enum.Status
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.util.ERROR_EVENT
import com.trung.applicationdoctor.util.UIEvent
import com.trung.applicationdoctor.util.extension.isNetworkConnected
import kotlinx.coroutines.*

class MainViewModel(
    private val defaultDispatcher: CoroutineDispatcher,
    private val channelApiRepository: ChannelApiRepository,
    private val channelCategoryRoomRepository: ChannelCategoryRoomRepository
) : BaseViewModel() {

    //the variable LiveData allChannelCategory which get all tab information from ROOM  automatically (when data of DB have any changes)
    val allChannelCategoryEntity: MutableLiveData<List<ChannelCategory>> = MutableLiveData<List<ChannelCategory>>()

    var allChannelCategory: LiveData<List<ChannelCategory>> =
        channelCategoryRoomRepository.allChannelCategory

    val searchLiveData = MutableLiveData<String?>()

    val isSearchDisplayed = ObservableBoolean(false)

    val currentTab = ObservableField<String>()

    init {
        getChannelCategoryApi()
    }

    /**
     * get tab names for TabLayout in activity_main.xml from API and save them to ROOM,
     * then the variable LiveData allChannelCategory which get all tab information from ROOM  automatically (when data of DB have any changes)
     * and update them directly to UI thanks to fun setTabTitle() in BindingAdapter.kt
     */
    fun getChannelCategoryApi() {
        try {
            viewModelScope.launch(defaultDispatcher) {
                if (ApplicationDoctor.context.isNetworkConnected) {
                    channelApiRepository.getCategoryList().let { baseApiResult ->
                        when (baseApiResult.status) {
                            Status.SUCCESS -> {
                                baseApiResult.data?.let { result ->
                                    when (result.code) {
                                        "1000" -> {
                                            insertAllToChannelCategoryDb(
                                                result.dataArray
                                            ).let {
                                                allChannelCategoryEntity.postValue(result.dataArray)
                                            }
                                        }
                                        else -> {
                                            _uiEvent.postValue(UIEvent(ERROR_EVENT,result.codeMsg))
                                        }
                                    }
                                }
                            }

                            Status.ERROR -> {
                                _uiEvent.postValue(UIEvent(ERROR_EVENT,baseApiResult.message))
                            }

                            Status.LOADING -> {

                            }
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
    suspend fun insertAllToChannelCategoryDb(
        listChannelCategory: List<ChannelCategory>
    ) : List<Long>? {
        return channelCategoryRoomRepository.insertAll(listChannelCategory)
    }
}