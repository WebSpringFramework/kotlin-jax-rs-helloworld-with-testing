package jaxrs.resources

import com.nhaarman.mockito_kotlin.whenever
import jaxrs.Application
import jaxrs.TestBindings
import org.assertj.core.api.Assertions.assertThat
import org.glassfish.jersey.test.JerseyTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import services.DataService
import services.HelloJson
import javax.ws.rs.core.MediaType
import org.junit.Test as TestV4

class `HTTP HelloWorldResource should` : JerseyTest(Application(TestBindings())) {
    @TestV4
    fun `returns 200`() {
        val statusCode = target("helloWorld").request().get().status
        assertThat(statusCode).isEqualTo(200)
    }

    @TestV4
    fun `returns JSON type`() {
        val mediaType = target("helloWorld").request().get().mediaType
        assertThat(mediaType).isEqualTo(MediaType.APPLICATION_JSON_TYPE)
    }
}

@ExtendWith(MockitoExtension::class)
class `HelloWorldResource Should` {

    private fun createHelloJson(prop1: Int = 1, prop2: String = "test") = HelloJson(prop1, prop2)

    @Mock
    lateinit var mockDataService: DataService

    @InjectMocks
    lateinit var helloWorldResource: HelloWorldResource


    @Test
    fun `returns same number of elements as DataService`() {
        whenever(mockDataService.all()).thenReturn(listOf(createHelloJson(), createHelloJson()))

        assertThat(helloWorldResource.helloWorld()).hasSize(2)
    }

    @Test
    fun `contains item from returned list`() {
        val expectedObject = createHelloJson(prop1 = 2)

        whenever(mockDataService.all()).thenReturn(listOf(expectedObject))

        assertThat(helloWorldResource.helloWorld()).contains(expectedObject)
    }
}
