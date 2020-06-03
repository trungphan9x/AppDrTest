package com.trung.applicationdoctor.data.repository.room

import androidx.lifecycle.LiveData
import com.trung.applicationdoctor.data.db.dao.ChannelListDao
import com.trung.applicationdoctor.data.db.entity.ChannelListEntity
import com.trung.applicationdoctor.data.remote.response.ChannelList

class ChannelListRoomRepository(private val channelListDao: ChannelListDao) {
    val allChannelList: LiveData<List<ChannelListEntity>> =
        channelListDao.getChannelList()

    suspend fun insert(channelList: ChannelList) =
        channelListDao.insert(
            ChannelListEntity(
                boardIdx = channelList.boardIdx,
                boardType = channelList.boardType,
                insDate = channelList.insDate,
                title = channelList.title,
                imgPath = channelList.imgPath,
                replyCnt = channelList.replyCnt,
                likeCnt = channelList.likeCnt,
                myLikeYn = channelList.myLikeYn,
                category = channelList.category,
                contentsYn = channelList.contentsYn
            )
        )

    suspend fun insertAll(listChannelList: List<ChannelList>) {
        val listChannelListEntity = ArrayList<ChannelListEntity>()
        listChannelList.forEach {
            listChannelListEntity.add(
                ChannelListEntity(
                    boardIdx = it.boardIdx,
                    boardType = it.boardType,
                    insDate = it.insDate,
                    title = it.title,
                    imgPath = it.imgPath,
                    replyCnt = it.replyCnt,
                    likeCnt = it.likeCnt,
                    myLikeYn = it.myLikeYn,
                    category = it.category,
                    contentsYn = it.contentsYn
                )
            )
        }
        return channelListDao.insertAll(listChannelListEntity)
    }

    suspend fun deleteAll() {
        channelListDao.deleteAll()
    }
}