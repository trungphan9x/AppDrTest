package com.trung.applicationdoctor.ui.fragment.list

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.BaseViewModel
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelListRoomRepository
import com.trung.applicationdoctor.extension.getUserEmail
import com.trung.applicationdoctor.extension.isNetworkConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListChannelViewModel (private val channelApiRepository: ChannelApiRepository,
                            private val channelCategoryRoomRepository: ChannelCategoryRoomRepository,
                            private val channelListRoomRepository: ChannelListRoomRepository
) : BaseViewModel() {
    val tabInformation = ObservableField<ChannelCategory>()

    val allItemsByCategory = ObservableField<List<ChannelList>>()

    fun getItemsFromApi() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                if(ApplicationDoctor.context.isNetworkConnected){
                    if(tabInformation.get()?.categoryName == context.getString(R.string.all)) {
                        channelApiRepository.getChannelList(pageNum = 1, memberId = context.getUserEmail().toString()).let {
                            allItemsByCategory.set(it.dataArray)
                            insertAllToChannelListDb(it.dataArray)
                        }
                    }else {
                        channelApiRepository.getChannelList(pageNum = 1, memberId = context.getUserEmail().toString(), categoryId = tabInformation.get()?.categoryIdx).let {
                            allItemsByCategory.set(it.dataArray)
                        }
                    }
                } else {
                    if(tabInformation.get()?.categoryName == context.getString(R.string.all)) {
                        channelListRoomRepository.getListAll().let {
                            allItemsByCategory.set(it)
                        }

                    }else {
                        channelListRoomRepository.getListByCategory(tabInformation.get()?.categoryName!!).let {
                            allItemsByCategory.set(it)
                        }
                    }
                }

            } catch (ex: Exception) {

            }
        }
    }

    private fun insertAllToChannelListDb(listChannelList: List<ChannelList>) {
        viewModelScope.launch (Dispatchers.IO){
            channelListRoomRepository.insertAll(listChannelList)
        }
    }
}