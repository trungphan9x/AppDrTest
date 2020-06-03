package com.trung.applicationdoctor.di

import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.api.SignApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelDetailRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelListRoomRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { ChannelApiRepository(get()) }
    factory { SignApiRepository(get()) }
    factory { ChannelCategoryRoomRepository(get()) }
    factory { ChannelDetailRoomRepository(get()) }
    factory { ChannelListRoomRepository(get()) }
}