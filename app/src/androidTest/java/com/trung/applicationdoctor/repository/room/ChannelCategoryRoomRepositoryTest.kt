package com.trung.applicationdoctor.repository.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
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
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
class ChannelCategoryRoomRepositoryTest : KoinTest {

    private val categoryRoomRepository: ChannelCategoryRoomRepository by inject()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun before() {
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
            categoryRoomRepository.insert(
                ChannelCategory(
                    categoryIdx = "1",
                    categoryName = "hahaha"
                )
            ).let {
                Truth.assertThat(it).isNotNull()
            }
        }
    }

    @Test
    fun insertAListOfChannelCategory() {
        runBlocking {
            categoryRoomRepository.insertAll(
                listOf<ChannelCategory>(
                    ChannelCategory(
                        categoryIdx = "1",
                        categoryName = "hahaha"
                    ), ChannelCategory(
                        categoryIdx = "2",
                        categoryName = "hahaha"
                    )
                )

            ).let {
                Truth.assertThat(it).isEmpty()
            }
        }
    }

    @Test
    fun deleteAll() {
        runBlocking {
            categoryRoomRepository.deleteAll().let {
                Truth.assertThat(it).isNull()
            }
        }
    }

}