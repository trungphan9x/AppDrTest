package com.trung.applicationdoctor.repository.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
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
class ChannelApiRepositoryTest : KoinTest {

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

    @Test
    fun getCategoryList() {
        runBlocking {
            apiRepository.getCategoryList().let {
                Truth.assertThat(it.data?.dataArray).isNotEmpty()
            }
        }
    }

    @Test
    fun getAllChannelList() {
        runBlocking {
            apiRepository.getChannelList(memberIdx = "143", pageNum = 1).let {
                Truth.assertThat(it.data?.dataArray).isNotEmpty()
            }
        }
    }

    @Test
    fun getChannelListByCategory() {
        runBlocking {
            apiRepository.getChannelList(memberIdx = "143", pageNum = 1, categoryId = "2").let {
                Truth.assertThat(it.data?.dataArray).isNotEmpty()
            }
        }
    }

    @Test
    fun getChannelDetail() {
        runBlocking {
            apiRepository.getChannelDetail(memberIdx = "143", boardId = "3").let {
                Truth.assertThat(it.data).isNotNull()
            }
        }
    }
}