package com.trung.applicationdoctor.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trung.applicationdoctor.data.db.entity.ChannelListEntity
@Dao
interface ChannelListDao {

    @Query("SELECT * from channel_list")
    fun getChannelList(): LiveData<List<ChannelListEntity>>

    @Query("SELECT * from channel_list")
    suspend fun getListAll(): List<ChannelListEntity>?

    @Query("SELECT * from channel_list where category == :category")
    suspend fun getListByCategory(category: String): List<ChannelListEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channelList: ChannelListEntity) : Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listChannelList: List<ChannelListEntity>) : List<Long>?

    @Query("DELETE FROM channel_list")
    suspend fun deleteAll() : Int?
}