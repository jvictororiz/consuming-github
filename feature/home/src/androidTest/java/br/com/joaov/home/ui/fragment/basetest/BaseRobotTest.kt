package br.com.joaov.home.ui.fragment.basetest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import br.com.joaov.home.ui.HomeActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.koin.test.KoinTest

internal open class BaseRobotTest<T : Fragment> : KoinTest {
    
    @get:Rule
    protected lateinit var scenario: FragmentScenario<T>
    
    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    protected val mockWebServer by lazy { MockWebServer() }
    
    fun startServer() {
        mockWebServer.start(8888)
    }
    
    fun closeFragment() {
        mockWebServer.shutdown()
        scenario.close()
    }
    
    protected fun prepareSuccess(pathResponse: String) {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(readJson(pathResponse))
        mockWebServer.enqueue(mockResponse)
    }
    
    protected fun prepareError(codeError: Int = 500, noConnection: Boolean = false) {
        if (noConnection) {
            mockWebServer.shutdown()
            return
        }
        mockWebServer.enqueue(MockResponse().setResponseCode(codeError).setBody("{}"))
    }
    
    fun readJson(path: String): String =
        this@BaseRobotTest::class.java.getResourceAsStream(path)?.readBytes()
            ?.toString(Charsets.UTF_8) ?: ""
}