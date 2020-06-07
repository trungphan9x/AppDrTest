package com.trung.applicationdoctor.data.repository.room

import androidx.lifecycle.LiveData
import com.trung.applicationdoctor.data.db.dao.ChannelDetailDao
import com.trung.applicationdoctor.data.db.entity.ChannelDetailEntity
import com.trung.applicationdoctor.data.remote.response.ChannelDetail

class ChannelDetailRoomRepository(private val channelDetailDao: ChannelDetailDao) {
    val allChannelDetail: LiveData<ChannelDetailEntity> = channelDetailDao.getChannelDetail()

    fun getChannelDetailLiveData(boardId: String?) :  LiveData<ChannelDetailEntity?> {
        return channelDetailDao.getChannelDetailLiveData(boardId)
    }

    suspend fun getChannelDetail(boardId: String) : ChannelDetail? {
        return channelDetailDao.getChannelDetail(boardId).let {
            it?.let {
                ChannelDetail(
                    boardIdx = it.boardIdx,
                    listCnt = it.listCnt,
                    corpIdx = it.corpIdx,
                    contentsYn = it.contentsYn,
                    title = it.title,
                    boardImg = it.boardImg,
                    viewCnt = it.viewCnt,
                    contents = it.contents,
                    insDate = it.insDate,
                    replyCnt = it.replyCnt,
                    likeCnt = it.likeCnt,
                    myLikeYn = it.myLikeYn,
                    myScrapYn = it.myScrapYn,
                    category =  it.category
                )
            }
        }
    }

    suspend fun insert(channelDetail: ChannelDetail) =
        channelDetailDao.insert(
            ChannelDetailEntity(
                boardIdx = channelDetail.boardIdx,
                listCnt = channelDetail.listCnt,
                corpIdx = channelDetail.corpIdx,
                contentsYn = channelDetail.contentsYn,
                title = channelDetail.title,
                boardImg = channelDetail.boardImg,
                viewCnt = channelDetail.viewCnt,
                contents = channelDetail.contents,
                insDate = channelDetail.insDate,
                replyCnt = channelDetail.replyCnt,
                likeCnt = channelDetail.likeCnt,
                myLikeYn = channelDetail.myLikeYn,
                myScrapYn = channelDetail.myScrapYn,
                category = channelDetail.category
            )
        )

    suspend fun insertAll(listChannelDetail: List<ChannelDetail>) : List<Long>? {
        val listPhotoDetailEntity = ArrayList<ChannelDetailEntity>()
        listChannelDetail.forEach {
            listPhotoDetailEntity.add(
                ChannelDetailEntity(
                    boardIdx = it.boardIdx,
                    listCnt = it.listCnt,
                    corpIdx = it.corpIdx,
                    contentsYn = it.contentsYn,
                    title = it.title,
                    boardImg = it.boardImg,
                    viewCnt = it.viewCnt,
                    contents = it.contents,
                    insDate = it.insDate,
                    replyCnt = it.replyCnt,
                    likeCnt = it.likeCnt,
                    myLikeYn = it.myLikeYn,
                    myScrapYn = it.myScrapYn,
                    category =  it.category
                )
            )
        }
        return channelDetailDao.insertAll(listPhotoDetailEntity)
    }

    suspend fun deleteAll() : Int? {
        return channelDetailDao.deleteAll()
    }
}