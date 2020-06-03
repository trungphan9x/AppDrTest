package com.trung.applicationdoctor.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channel_category")
data class ChannelCategoryEntity (
    @PrimaryKey val categoryIdx: String,
    @ColumnInfo(name = "categoryName") val categoryName: String,
    @ColumnInfo(name = "insDate") val insDate: String? = null
)