package br.com.joaov.home.ui.fragment.home

import android.app.Instrumentation
import android.os.SystemClock
import android.view.MotionEvent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import br.com.joaov.home.R
import br.com.joaov.home.ui.fragment.ListRepositoriesFragment
import br.com.joaov.home.ui.fragment.basetest.BaseRobotTest
import br.com.joaov.persistence.data.AppDatabase
import br.com.joaov.persistence.data.entity.OwnerEntity
import br.com.joaov.persistence.data.entity.RepositoryEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.inject


internal class HomeFragmentRobot : BaseRobotTest<ListRepositoriesFragment>(), KoinTest {
    
    private val database by inject<AppDatabase>()
    private val repositoryDao by lazy { database.repositoryDao() }
    
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    init {
        startServer()
    }
    
    fun startTest() = apply {
        runBlocking { repositoryDao.deleteAll() }
    }
    
    fun launchFragment() = apply {
        scenario = launchFragmentInContainer(null, R.style.HomeTheme)
        time()
    }
    
    
    fun tapOnRetry() = apply {
        onView(withId(R.id.btnRetry)).perform(click())
    }
    
    fun tapOnItem() = apply {
        onView(withId(R.id.rvRepositories))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }
    
    fun injectUsersSuccessMock() = apply {
        prepareSuccess(PATH_REPOSITORIES)
    }
    
    fun injectUsersSuccessPage2Mock() = apply {
        prepareSuccess(PATH_REPOSITORIES_PAGE2)
    }
    
    fun injectUsersErrorNoConnectionMock() = apply {
        prepareError(500, true)
    }
    
    fun injectUsersErrorServerMock() = apply {
        prepareError(404)
    }
    
    fun injectRepositoriesDatabase() = apply {
        val repositories = getMockRepositoriesEntity()
        runBlocking { repositoryDao.saveRepositories(repositories) }
    }
    
    
    fun validateRepositoriesScreen() = apply {
        onView(withText("teste mock")).check(matches(isDisplayed()))
    }
    
    fun validateContainsLocalData() = apply {
        onView(withText("João Victor Holanda")).check(matches(isDisplayed()))
    }
    
    fun validateContainsMessageHeaderError() = apply {
        onView(withId(R.id.tvWithoutConnectionWithCache)).check(matches(isDisplayed()))
    }
    
    fun validateNotContainsErrorScreen() = apply {
        onView(withId(R.id.btnRetry)).check(matches(not(isDisplayed())))
    }
    
    fun validateNoConnectionMessageError() = apply {
        val expectedMessageError =
            "Tem algo de errado com a sua conexão. Por favor, verifique sua internet"
        onView(withText(expectedMessageError)).check(matches(isDisplayed()))
    }
    
    fun validateContainsErrorScreen() = apply {
        onView(withId(R.id.imageViewNoWifi)).check(matches(isDisplayed()))
    }
    
    fun validateFooterError() = apply {
        onView(withText("Falha na conexão com o servidor, por favor, tente novamente mais tarde")).check(matches(isDisplayed()))
    }
    
    fun validateEmptyUsersScreen() = apply {
        onView(withText("JetBrains/kotlin")).check(doesNotExist())
    }
    
    fun scrollToEndRecyclerView() = apply {
        onView(withId(R.id.rvRepositories)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(18)
        )
    }
    
    fun scrollToBottom() = apply {
        swipeTo(2000, 0, 1000)
    }
    
    fun validateRepositoriesPage2Screen() = apply {
        onView(withText("teste mock page 2")).check(matches(isDisplayed()))
    }
    
    fun endTest() = apply {
        closeFragment()
    }
    
    private fun time() = apply {
        runBlocking {
            delay(1000L)
        }
    }
    
    private fun swipeTo(start: Int, end: Int, delay: Int) {
        val downTime = SystemClock.uptimeMillis()
        var eventTime = SystemClock.uptimeMillis()
        val inst: Instrumentation = getInstrumentation()
        var event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, 500f, start.toFloat(), 0)
        inst.sendPointerSync(event)
        eventTime = SystemClock.uptimeMillis() + delay
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, 500f, end.toFloat(), 0)
        inst.sendPointerSync(event)
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, 500f, end.toFloat(), 0)
        inst.sendPointerSync(event)
        SystemClock.sleep(1000)
    }
    
    private fun getMockRepositoriesEntity() = listOf(
        RepositoryEntity(
            1, "João Victor", "João Victor Holanda", 1234, 4321, "www.google.com", 1, OwnerEntity(1, "jvictororiz", "https://media-exp1.licdn.com/dms/image/C4E03AQG5SGSz7BtIjA/profile-displayphoto-shrink_200_200/0/1563817355347?e=1658966400&v=beta&t=UjCHiSc3PBt2vKdKqmZGQbQvcTx59vthWiE9HXYDXnI", "www.google.com")
        ),
    )
    
    companion object {
        private const val PATH_REPOSITORIES = "/json/response_repositories_page1.json"
        private const val PATH_REPOSITORIES_PAGE2 = "/json/response_repositories_page2.json"
        fun instance() = HomeFragmentRobot()
        
    }
}