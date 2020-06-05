package com.trung.applicationdoctor.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channel_list")
data class ChannelListEntity (
    @PrimaryKey
    @ColumnInfo(name = "boardIdx") val boardIdx: String,
    @ColumnInfo(name = "boardType") val boardType: String?,
    @ColumnInfo(name = "insDate") val insDate: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "imgPath") val imgPath: String?,
    @ColumnInfo(name = "replyCnt") val replyCnt: String?,
    @ColumnInfo(name = "likeCnt") val likeCnt: String?,
    @ColumnInfo(name = "myLikeYn") val myLikeYn: String?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "contentsYn") val contentsYn: String?
)