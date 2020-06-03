package com.trung.applicationdoctor.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trung.applicationdoctor.data.db.entity.ChannelDetailEntity
@Dao
interface ChannelDetailDao {

    @Query("SELECT * from channel_detail")
    fun getChannelDetail(): LiveData<ChannelDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channelDetail: ChannelDetailEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(listChannelDetail: List<ChannelDetailEntity>)

    @Query("DELETE FROM channel_detail")
    suspend fun deleteAll()
}