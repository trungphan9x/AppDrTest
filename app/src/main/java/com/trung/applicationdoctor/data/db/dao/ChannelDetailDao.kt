package com.trung.applicationdoctor.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trung.applicationdoctor.data.db.entity.ChannelDetailEntity
@Dao
interface ChannelDetailDao {

    @Query("SELECT * from channel_detail ORDER BY timestamp DESC LIMIT 1 ")
    fun getChannelDetail(): LiveData<ChannelDetailEntity>

    @Query("SELECT * from channel_detail Where boardIdx == :boardIdx")
    fun getChannelDetailLiveData(boardIdx: String?): LiveData<ChannelDetailEntity?>

    @Query("SELECT * from channel_detail Where boardIdx == :boardIdx")
    suspend fun getChannelDetail(boardIdx: String): ChannelDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channelDetail: ChannelDetailEntity) : Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(listChannelDetail: List<ChannelDetailEntity>) : List<Long>?

    @Query("DELETE FROM channel_detail")
    suspend fun deleteAll() : Int?
}