package com.trung.applicationdoctor.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.trung.applicationdoctor.data.db.AppDoctorDatabase
import com.trung.applicationdoctor.data.remote.response.ChannelDetail
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelDetailRoomRepository
import com.trung.applicationdoctor.di.*
import com.trung.applicationdoctor.ui.activity.detail.DetailViewModel
import com.trung.applicationdoctor.util.MainCoroutineScopeRule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest1 : KoinTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val roomRepository: ChannelDetailRoomRepository by inject()
    private val apiRepository: ChannelApiRepository by inject()


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

    /**
     * Test with board_id has been deleted or moved.
     */
    @Test
    fun getBoardIdUnavailable() {
        val viewModel = DetailViewModel(testDispatcher, roomRepository, apiRepository)
        runBlocking {
            viewModel.getDetailChannel("1")
            delay(1000)
            Truth.assertThat(viewModel.channelDetail.value).isNull()

            val roomResult = roomRepository.getChannelDetail("1")
            delay(1000)
            Truth.assertThat(roomResult).isNull()
        }
    }

    /**
     * Test with board_id is available
     */
    @Test
    fun getBoardIdAvailable() {
        val viewModel = DetailViewModel(testDispatcher, roomRepository, apiRepository)
        runBlocking {
            viewModel.getDetailChannel("2")
            delay(2000)
            Truth.assertThat(viewModel.channelDetail.value.toString()).isEmpty()


            val roomResult = roomRepository.getChannelDetail("2")
            delay(1000)
            Truth.assertThat(roomResult).isNotNull()


        }
    }
}