package com.trung.applicationdoctor.ui.fragment.list

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.core.BaseViewModel
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelListRoomRepository
import com.trung.applicationdoctor.util.extension.getUserEmail
import com.trung.applicationdoctor.util.extension.isNetworkConnected
import com.trung.applicationdoctor.ui.fragment.list.ListChannelFragment.Companion.ERROR_MESSAGE
import com.trung.applicationdoctor.util.UIEvent
import com.trung.applicationdoctor.util.extension.getUserMemberIdx
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListChannelViewModel (private val defaultDispatcher: CoroutineDispatcher,
                            private val channelApiRepository: ChannelApiRepository,
                            private val channelListRoomRepository: ChannelListRoomRepository
) : BaseViewModel() {
    val tabInformation = ObservableField<ChannelCategory>()

    val allItemsByCategory = ObservableField<List<ChannelList>>()

    /**
     * if it has internet, get all items for RecyclerView in fragment_list_channel.xml from API and save them to ROOM incase of tab 전체,
     * if it has internet, get specific items of specific tab for RecyclerView in fragment_list_channel.xml from API incase of other tabs,
     * else if it has no internet, get all items or items of specific tab for RecyclerView in fragment_list_channel.xml from ROOM in case of tab 전체 or other tabs respectively
     */
    fun getItemsFromApi() {
        viewModelScope.launch (defaultDispatcher) {
            try {
                if(ApplicationDoctor.context.isNetworkConnected){
                    if(tabInformation.get()?.categoryName == context.getString(R.string.all) ) {
                        channelApiRepository.getChannelList(pageNum = 1, memberIdx = context.getUserMemberIdx().toString()).let { result->
                            result.data?.let {
                                when (it.code) {
                                    "1000" -> {
                                        allItemsByCategory.set(it.dataArray)
                                        insertAllToChannelListDb(it.dataArray)
                                    }

                                    else -> {
                                        _uiEvent.postValue(UIEvent(ERROR_MESSAGE, it.codeMsg))
                                    }
                                }
                            }
                        }
                    }else {
                        channelApiRepository.getChannelList(pageNum = 1, memberIdx = context.getUserMemberIdx().toString(), categoryId = tabInformation.get()?.categoryIdx).let { result ->
                            result.data?.let {
                                when (it.code) {
                                    "1000" -> {
                                        allItemsByCategory.set(it.dataArray)
                                    }
                                    else -> {
                                        _uiEvent.postValue(UIEvent(ERROR_MESSAGE, it.codeMsg))
                                    }
                                }
                            }

                        }
                    }
                } else {
                    if(tabInformation.get()?.categoryName == context.getString(R.string.all)) {
                        channelListRoomRepository.getListAll().let {
                            if(it != null) {
                                allItemsByCategory.set(it)
                            } else {
                                _uiEvent.postValue(UIEvent(ERROR_MESSAGE, "You have no internet connection"))
                            }

                        }

                    }else {
                        channelListRoomRepository.getListByCategory(tabInformation.get()?.categoryName!!).let {
                            if(it != null) {
                                allItemsByCategory.set(it)
                            } else {
                                _uiEvent.postValue(UIEvent(ERROR_MESSAGE, "You have no internet connection"))
                            }
                        }
                    }
                }

            } catch (ex: Exception) {

            } finally {

            }
        }
    }

    /**
     * Insert all list of tab 전체 which got from API into ROOM for reading offline
     */
    private fun insertAllToChannelListDb(listChannelList: List<ChannelList>) {
        viewModelScope.launch (defaultDispatcher){
            channelListRoomRepository.insertAll(listChannelList)
        }
    }
}