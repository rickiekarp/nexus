package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User
import net.rickiekarp.loginserver.dao.UserDAO
import net.rickiekarp.loginserver.dto.TokenDTO
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit

class AccountApiTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    @Mock
    private val repo: UserDAO? = null

    @InjectMocks
    private val testingObject: AccountApi? = null

    @Before
    fun initMocks() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCheckCreateWithUser() {
        val user = User("user", "pass")
        checkType(user)
    }

    @Test
    fun testCheckCreateWithoutUser() {
        checkType(null)
    }

    private fun checkType(user: User?) {
        val credentials = Credentials()
        credentials.username = "a"
        credentials.password = "b"
        `when`<User>(repo!!.registerUser(credentials)).thenReturn(user)

        val response = testingObject!!.create(credentials)
        if (user == null) {
            if (response.body !is ResultDTO) {
                Assert.fail("Body is not: " + ResultDTO::class.java.simpleName + " / Was: " + response.body)
            }
            Assert.assertTrue(!(response.body as ResultDTO).result.isEmpty())
        } else {
            if (response.body !is TokenDTO) {
                Assert.fail("Body is not: " + TokenDTO::class.java.simpleName + " / Was: " + response.body)
            }
            Assert.assertTrue(!(response.body as TokenDTO).token.isEmpty())
        }
    }
}
