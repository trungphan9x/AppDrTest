package com.trung.applicationdoctor.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.trung.applicationdoctor.data.db.AppDoctorDatabase
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelDetailRoomRepository
import com.trung.applicationdoctor.di.*
import com.trung.applicationdoctor.ui.activity.detail.DetailViewModel
import com.trung.applicationdoctor.ui.activity.main.MainViewModel
import com.trung.applicationdoctor.util.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.*
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
class MainViewModelTest : KoinTest{
    private val testDispatcher = TestCoroutineDispatcher()
    private val apiRepository : ChannelApiRepository by inject()
    private val categoryRoomRepository: ChannelCategoryRoomRepository by inject()
    //private val viewModel: MainViewModel by inject()

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
//    @Test
//    fun getBoardIdUnavailable() {
//
//        viewModel.getChannelCategoryApi() {
//            Truth.assertThat(viewModel.channelDetail.value.toString()).isEqualTo(expectedValue)
//        }
//    }

    /**
     * Test with board_id is available
     */
    @Test
    fun getListCategory() {

        runBlocking {
            val mainViewModel = MainViewModel(testDispatcher, apiRepository, categoryRoomRepository)

            mainViewModel.getChannelCategoryApi()
            delay(1000)
            Truth.assertThat(mainViewModel.allChannelCategoryEntity.value).isNotEmpty()

        }


    }
}