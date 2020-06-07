package com.trung.applicationdoctor.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.repository.api.SignApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.di.*
import com.trung.applicationdoctor.ui.activity.signin.SignInViewModel
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
class ChannelCategoryRoomRepositoryTest : KoinTest {
    private val testDispatcher = TestCoroutineDispatcher()
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
    fun checkSignIn() {
        runBlocking {
            val signViewModel = SignInViewModel(testDispatcher, signApiRepository)
            //signApiRepository.signIn(memberId = "ttt@gmail.com", memberPw = "12122012gv!", gcmKey = 2, deviceOS = "A")
            signViewModel.email.set("ttt@gmail.com")
            signViewModel.password.set("12122012gv!")
            signViewModel.clickLogIn()
            delay(1000)
            Truth.assertThat(signViewModel.resultSignIn.value).isNotNull()

        }
    }
}

