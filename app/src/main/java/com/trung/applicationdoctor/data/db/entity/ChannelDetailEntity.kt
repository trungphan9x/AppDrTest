package com.trung.applicationdoctor.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.trung.applicationdoctor.data.db.converter.ArrayListConverter

@Entity(tableName = "channel_detail")
@TypeConverters(ArrayListConverter::class)
data class ChannelDetailEntity (
    @PrimaryKey val boardIdx: String,
    @ColumnInfo(name = "listCnt") val listCnt: Long?,
    @ColumnInfo(name = "corpIdx") val corpIdx: String?,
    @ColumnInfo(name = "contentsYn") val contentsYn: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "boardImg") val boardImg: String?,
    @ColumnInfo(name = "viewCnt") val viewCnt: String?,
    @ColumnInfo(name = "contents") val contents: String?,
    @ColumnInfo(name = "insDate") val insDate: String?,
    @ColumnInfo(name = "replyCnt") val replyCnt: String?,
    @ColumnInfo(name = "likeCnt") val likeCnt: String?,
    @ColumnInfo(name = "myLikeYn") val myLikeYn: String?,
    @ColumnInfo(name = "myScrapYn") val myScrapYn: String?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "timestamp") val timestamp: Long? = System.currentTimeMillis()

)