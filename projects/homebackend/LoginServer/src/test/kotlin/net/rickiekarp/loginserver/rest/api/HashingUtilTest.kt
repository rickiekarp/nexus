package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.loginserver.utils.HashingUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class HashingUtilTest {

    @InjectMocks
    private val testingObject: HashingUtil? = null

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testCheckCreateWithUser() {
        val password = "password"
        val expectedPassword = "a4be62c06f472ccb61059b14122c4684e9a415d25f4873f389"
        val actualPassword = testingObject!!.generateStrongPasswordHash(password)
        Assert.assertEquals(expectedPassword, actualPassword)
    }
}
