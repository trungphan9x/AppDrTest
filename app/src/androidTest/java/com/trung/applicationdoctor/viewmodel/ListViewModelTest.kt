package com.trung.applicationdoctor.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelListRoomRepository
import com.trung.applicationdoctor.di.*
import com.trung.applicationdoctor.ui.fragment.list.ListChannelViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListViewModelTest : KoinTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val apiRepository: ChannelApiRepository by inject()
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
    fun checkIfGetItemsFromApiAndInsertToDBAreRight() {
        val listViewModel = ListChannelViewModel(testDispatcher, apiRepository, listRoomRepository)
        runBlocking {
            listViewModel.tabInformation.set(ChannelCategory(
                categoryIdx = "6",
                categoryName = "장보기꿀팁"
            ))

            listViewModel.getItemsFromApi()
            delay(1000)
            Truth.assertThat(listViewModel.allItemsByCategory.get()).isNotNull()

            val listItems = listRoomRepository.getListAll()
            delay(1000)
            Truth.assertThat(listItems).isNotNull()

        }
    }
}