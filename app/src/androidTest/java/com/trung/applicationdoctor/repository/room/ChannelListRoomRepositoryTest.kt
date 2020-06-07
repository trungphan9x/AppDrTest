package com.trung.applicationdoctor.repository.room

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.trung.applicationdoctor.data.db.AppDoctorDatabase
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelListRoomRepository
import com.trung.applicationdoctor.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ChannelListRoomRepositoryTest : KoinTest {

    private val listRoomRepository: ChannelListRoomRepository by inject()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun before() {
        MockitoAnnotations.initMocks(this)
        stopKoin()

        startKoin {
            modules(
                viewModelModuleAndroidTest,
                roomModuleAndroidTest,
                repositoryModuleAndroidTest,
                apiModuleAndroidTest,
                networkModuleAndroidTest
            )
        }
    }

    @After
    @Throws(Exception::class)
    fun after() {
        stopKoin()
    }


    @Test
    fun insertOneElement() {
        runBlocking {
            listRoomRepository.insert(
                ChannelList(
                    boardIdx = "1",
                    boardType = "ssssss",
                    insDate = "ssssss",
                    title = "ssssss",
                    imgPath = "ssssss",
                    replyCnt = "ssssss",
                    likeCnt = "ssssss",
                    myLikeYn = "ssssss",
                    category = "ssssss",
                    contentsYn = "ssssss"
                )
            ).let {
                Truth.assertThat(it).isNotNull()
            }
        }
    }

    @Test
    fun insertAListOfChannelCategory() {
        runBlocking {
            listRoomRepository.insertAll(
                listOf<ChannelList>(
                    ChannelList(
                        boardIdx = "1",
                        boardType = "ssssss",
                        insDate = "ssssss",
                        title = "ssssss",
                        imgPath = "ssssss",
                        replyCnt = "ssssss",
                        likeCnt = "ssssss",
                        myLikeYn = "ssssss",
                        category = "ssssss",
                        contentsYn = "ssssss"
                    ),
                    ChannelList(
                        boardIdx = "22222",
                        boardType = "ssssss",
                        insDate = "ssssss",
                        title = "ssssss",
                        imgPath = "ssssss",
                        replyCnt = "ssssss",
                        likeCnt = "ssssss",
                        myLikeYn = "ssssss",
                        category = "ssssss",
                        contentsYn = "ssssss"
                    )
                )

            ).let {
                Truth.assertThat(it).isNotEmpty()
            }
        }
    }

    @Test
    fun deleteAll() {
        runBlocking {
            listRoomRepository.deleteAll().let {
                Truth.assertThat(it).isNull()
            }
        }
    }

    @Test
    fun getListAll() {
        runBlocking {
            listRoomRepository.getListAll().let {
                Truth.assertThat(it).isEmpty()
            }
        }
    }

}