package com.trung.applicationdoctor.repository.room

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.trung.applicationdoctor.data.db.AppDoctorDatabase
import com.trung.applicationdoctor.data.db.entity.ChannelDetailEntity
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.remote.response.ChannelDetail
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelDetailRoomRepository
import com.trung.applicationdoctor.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
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
class ChannelDetailRoomRepositoryTest : KoinTest {

    private val detailRoomRepository: ChannelDetailRoomRepository by inject()

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
            detailRoomRepository.insert(
                ChannelDetail(
                    boardIdx = "10000",
                    listCnt = 2,
                    corpIdx = "zzzz",
                    contentsYn = "zzzz",
                    title = "zzzz",
                    boardImg = "zzzz",
                    viewCnt = "222333",
                    contents = "zzzz",
                    insDate = "zzzz",
                    replyCnt = "zzzz",
                    likeCnt = "zzzz",
                    myLikeYn = "zzzz",
                    myScrapYn = "zzzz",
                    category = "zzzz"
                )
            ).let {
                Truth.assertThat(it).isNotNull()
            }
        }
    }

    @Test
    fun insertAListOfChannelCategory() {
        runBlocking {
            detailRoomRepository.insertAll(
                listOf<ChannelDetail>(
                    ChannelDetail(
                        boardIdx = "1",
                        listCnt = 2,
                        corpIdx = "zzzz",
                        contentsYn = "zzzz",
                        title = "zzzz",
                        boardImg = "zzzz",
                        viewCnt = "222333",
                        contents = "zzzz",
                        insDate = "zzzz",
                        replyCnt = "zzzz",
                        likeCnt = "zzzz",
                        myLikeYn = "zzzz",
                        myScrapYn = "zzzz",
                        category = "zzzz"
                    ),
                    ChannelDetail(
                        boardIdx = "2",
                        listCnt = 24,
                        corpIdx = "zzzz",
                        contentsYn = "zzzz",
                        title = "zzzz",
                        boardImg = "zzzz",
                        viewCnt = "222333",
                        contents = "zzzz",
                        insDate = "zzzz",
                        replyCnt = "zzzz",
                        likeCnt = "zzzz",
                        myLikeYn = "zzzz",
                        myScrapYn = "zzzz",
                        category = "zzzz"
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
            detailRoomRepository.deleteAll().let {
                Truth.assertThat(it).isNotNull()
            }
        }
    }

    @Test
    fun getChannelDetailTest() {
        runBlocking {
            detailRoomRepository.getChannelDetail(boardId = "2").let {
                Truth.assertThat(it).isNull()
            }
        }
    }

}