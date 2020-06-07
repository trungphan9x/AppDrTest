package com.trung.applicationdoctor.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trung.applicationdoctor.data.db.entity.ChannelCategoryEntity
@Dao
interface ChannelCategoryDao {

    @Query("SELECT * from channel_category ORDER BY categoryName ASC")
    fun getChannelCategory(): LiveData<List<ChannelCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channelCategory: ChannelCategoryEntity): Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(listChannelCategory: List<ChannelCategoryEntity>) : List<Long>?

    @Query("DELETE FROM channel_category")
    suspend fun deleteAll() : Int?
}