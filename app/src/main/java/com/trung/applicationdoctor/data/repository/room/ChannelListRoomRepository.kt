package com.trung.applicationdoctor.data.repository.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.trung.applicationdoctor.data.db.dao.ChannelListDao
import com.trung.applicationdoctor.data.db.entity.ChannelListEntity
import com.trung.applicationdoctor.data.remote.response.ChannelList

class ChannelListRoomRepository(private val channelListDao: ChannelListDao) {

    val allChannelList: LiveData<List<ChannelList>> = Transformations.map(channelListDao.getChannelList()) {
        val listChannelList = ArrayList<ChannelList>()
        it?.forEach {channelListEntity ->
            listChannelList.add(
                ChannelList(
                    boardIdx = channelListEntity.boardIdx,
                    boardType = channelListEntity.boardType,
                    insDate = channelListEntity.insDate,
                    title = channelListEntity.title,
                    imgPath = channelListEntity.imgPath,
                    replyCnt = channelListEntity.replyCnt,
                    likeCnt = channelListEntity.likeCnt,
                    myLikeYn = channelListEntity.myLikeYn,
                    category = channelListEntity.category,
                    contentsYn = channelListEntity.contentsYn
                )
            )
        }

        return@map listChannelList
    }

    suspend fun getListAll(): List<ChannelList>? {
        val listChannelListAll = ArrayList<ChannelList>()
        channelListDao.getListAll()?.let {
            it.forEach {
                listChannelListAll.add(
                    ChannelList(
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
        }
        return listChannelListAll
    }

    suspend fun getListByCategory(category: String): List<ChannelList>? {
        val listChannelListByCategory = ArrayList<ChannelList>()
        channelListDao.getListByCategory(category)?.forEach {
            listChannelListByCategory.add(
                ChannelList(
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
        return listChannelListByCategory
    }

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

    suspend fun insertAll(listChannelList: List<ChannelList>) : List<Long>?{
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

    suspend fun deleteAll() : Int?{
        return channelListDao.deleteAll()
    }
}