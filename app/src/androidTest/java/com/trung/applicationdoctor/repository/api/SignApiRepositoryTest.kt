package com.trung.applicationdoctor.repository.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.trung.applicationdoctor.data.repository.api.SignApiRepository
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
@RunWith(MockitoJUnitRunner::class)
class SignApiRepositoryTest : KoinTest {

    private val signApiRepository: SignApiRepository by inject()

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
            signApiRepository.signIn(memberId = "ttt@gmail.com", memberPw = "12122012gv!", deviceOS = "A", gcmKey = 2).let {
                Truth.assertThat(it.data).isNull()
            }
        }
    }

}