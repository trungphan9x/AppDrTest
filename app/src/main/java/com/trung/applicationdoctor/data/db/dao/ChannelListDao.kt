package com.trung.applicationdoctor.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trung.applicationdoctor.data.db.entity.ChannelListEntity
@Dao
interface ChannelListDao {

    @Query("SELECT * from channel_list")
    fun getChannelList(): LiveData<List<ChannelListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channelList: ChannelListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listChannelList: List<ChannelListEntity>)

    @Query("DELETE FROM channel_list")
    suspend fun deleteAll()
}