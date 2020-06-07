package com.trung.applicationdoctor.data.repository.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.trung.applicationdoctor.data.db.dao.ChannelCategoryDao
import com.trung.applicationdoctor.data.db.entity.ChannelCategoryEntity
import com.trung.applicationdoctor.data.remote.response.ChannelCategory

class ChannelCategoryRoomRepository(private val channelCategoryDao: ChannelCategoryDao) {
    val allChannelCategoryEntity: LiveData<List<ChannelCategoryEntity>> = channelCategoryDao.getChannelCategory()
    val allChannelCategory: LiveData<List<ChannelCategory>> = Transformations.map(channelCategoryDao.getChannelCategory()) {
        val listChannelCategory = ArrayList<ChannelCategory>()
        it.forEach {channelCategoryEntity ->
            listChannelCategory.add(
                ChannelCategory(
                    categoryIdx = channelCategoryEntity.categoryIdx,
                    categoryName = channelCategoryEntity.categoryName,
                    insDate = channelCategoryEntity.insDate
                )
            )
        }

        listChannelCategory
    }

    suspend fun insert(channelCategory: ChannelCategory) =
        channelCategoryDao.insert(
            ChannelCategoryEntity(
                categoryIdx = channelCategory.categoryIdx,
                categoryName = channelCategory.categoryName,
                insDate = channelCategory.insDate
            )
        )

    suspend fun insertAll(listChannelCategory: List<ChannelCategory>) : List<Long>?  {
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

    suspend fun deleteAll() : Int? {
        return channelCategoryDao.deleteAll()
    }
}