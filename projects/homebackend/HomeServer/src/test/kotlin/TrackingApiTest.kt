import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.homeserver.dao.TrackingDao
import net.rickiekarp.homeserver.dto.WeightDto
import net.rickiekarp.homeserver.rest.api.TrackingApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit

class TrackingApiTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    @Mock
    private val repo: TrackingDao? = null

    @InjectMocks
    private val testingObject: TrackingApi? = null

    @Before
    fun initMocks() {
        val builder = BaseConfig.ConfigBuilder()
        builder.setupDirectory = "test"
        builder.applicationIdentifier = "identifier"
        BaseConfig.create(builder)

        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCheckCreateWithUser() {
        val list = ArrayList<WeightDto>()
        list.add(WeightDto())
        checkType(list)
    }

    private fun checkType(list: List<WeightDto>?) {
        `when`(repo!!.getWeightHistory(any(Int::class.java), any(Int::class.java))).thenReturn(list)
        val response = testingObject!!.getWeightList(10)
        Assert.assertTrue((response.body as List<WeightDto>).isNotEmpty())
    }
}