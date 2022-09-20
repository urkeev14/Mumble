package com.example.mumble.services.manager

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.usecase.CreateUserUseCase
import com.example.mumble.domain.usecase.DeleteUserUseCase
import com.example.mumble.domain.usecase.ReadAllUsersOnlineUseCase
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.UpdateCurrentUserUseCase
import com.example.mumble.services.ChatService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.InetAddress
import javax.inject.Inject

@HiltAndroidTest
class UserEntityDiscoveryManagerTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var nsdManager: NsdManager

    @Inject
    lateinit var createUserUseCase: CreateUserUseCase

    lateinit var readCurrentUserUseCase: ReadCurrentUserUseCase

    @Inject
    lateinit var deleteUserUseCase: DeleteUserUseCase

    @Inject
    lateinit var updateCurrentUserUseCase: UpdateCurrentUserUseCase

    @Inject
    lateinit var readAllUsersOnlineUseCase: ReadAllUsersOnlineUseCase

    private lateinit var sut: UserDiscoveryManager

    @Before
    fun setUp() {
        readCurrentUserUseCase = mockk()
        nsdManager = mockk(relaxed = true)
        hiltRule.inject()

        sut = UserDiscoveryManager(
            nsdManager,
            createUserUseCase,
            readCurrentUserUseCase,
            deleteUserUseCase,
            updateCurrentUserUseCase,
            readAllUsersOnlineUseCase
        )
        sut.isResolveListenerBusy = mockk()
        sut.start()
    }

    @After
    fun tearDown() {
        sut.stop()
    }

    @Test
    fun givenSutIsStarted_verifyThatDiscoveryListenerIsInitialized() {
        assertNotNull(sut.discoveryListener)
        assertNotNull(sut.resolveListener)
        verify(exactly = 1) {
            nsdManager.discoverServices(
                ChatService.SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, sut.discoveryListener
            )
        }
    }

    @Test
    fun givenNewUserIsFound_whenSutResolvesTheService_thenNewUserIsCreated(): Unit = runBlocking {
        val serviceInfo = mockk<NsdServiceInfo>().apply {
            every { serviceType } returns "_mumble._tcp."
            every { serviceName } returns "some_username"
            every { host } returns InetAddress.getByName("192.168.0.1")
            every { port } returns 2000
        }
        every { sut.isResolveListenerBusy.compareAndSet(false, true) } returns true
        every { sut.isResolveListenerBusy.set(any()) } just Runs
        every { readCurrentUserUseCase() } returns flowOf(UserEntity(username = "other_username"))

        sut.discoveryListener?.onServiceFound(serviceInfo)
        sut.resolveListener?.onServiceResolved(serviceInfo)
        val list = readAllUsersOnlineUseCase().drop(1).first()
        assert(list.isNotEmpty())
    }

    @Test
    fun givenNewServiceFound_whenResolverListenerIsBusy_thenNewServiceIsAddedToQueue() =
        runBlocking {
            val serviceInfo = mockk<NsdServiceInfo>().apply {
                every { serviceType } returns "_mumble._tcp."
                every { serviceName } returns "some_username"
                every { host } returns InetAddress.getByName("192.168.0.1")
                every { port } returns 2000
            }
            every { sut.isResolveListenerBusy.compareAndSet(false, true) } returns false
            every { sut.isResolveListenerBusy.set(any()) } just Runs
            every { readCurrentUserUseCase() } returns flowOf(UserEntity(username = "other_username"))

            sut.discoveryListener?.onServiceFound(serviceInfo)

            assert(sut.pendingNsdServices.contains(serviceInfo))
        }
}
