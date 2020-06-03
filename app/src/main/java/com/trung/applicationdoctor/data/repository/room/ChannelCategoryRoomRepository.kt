package com.trung.applicationdoctor.data.repository.room

import androidx.lifecycle.LiveData
import com.trung.applicationdoctor.data.db.dao.ChannelCategoryDao
import com.trung.applicationdoctor.data.db.entity.ChannelCategoryEntity
import com.trung.applicationdoctor.data.remote.response.ChannelCategory

class ChannelCategoryRoomRepository(private val channelCategoryDao: ChannelCategoryDao) {
    val allChannelCategory: LiveData<List<ChannelCategoryEntity>> = channelCategoryDao.getChannelCategory()

    suspend fun insert(channelCategory: ChannelCategory) =
        channelCategoryDao.insert(
            ChannelCategoryEntity(
                categoryIdx = channelCategory.categoryIdx,
                categoryName = channelCategory.categoryName,
                insDate = channelCategory.insDate
            )
        )

    suspend fun insertAll(listChannelCategory: List<ChannelCategory>) {
        val listChannelCategoryEntity = ArrayList<ChannelCategoryEntity>()
        listChannelCategory.forEach {
            listChannelCategoryEntity.add(
                ChannelCategoryEntity(
                    categoryIdx = it.categoryIdx,
                    categoryName = it.categoryName,
                    insDate = it.insDate
                )
            )
        }
        return channelCategoryDao.insertAll(listChannelCategoryEntity)
    }

    suspend fun deleteAll() {
        channelCategoryDao.deleteAll()
    }
}