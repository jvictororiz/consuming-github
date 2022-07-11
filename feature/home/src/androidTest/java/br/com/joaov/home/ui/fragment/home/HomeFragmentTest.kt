package br.com.joaov.home.ui.fragment.home

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
internal class HomeFragmentTest {
    
    private val robot by lazy { HomeFragmentRobot.instance() }
    
    @Test
    fun getRepositoriesWithSuccess() {
        robot
            .startTest()
            .injectUsersSuccessMock()
            .launchFragment()
            .validateRepositoriesScreen()
            .validateNotContainsErrorScreen()
            .endTest()
    }
    
    @Test
    fun getRepositoriesWithErrorServer() {
        robot
            .startTest()
            .injectUsersErrorServerMock()
            .launchFragment()
            .validateEmptyUsersScreen()
            .endTest()
    }
    
    @Test
    fun getRepositoriesWithErrorConnection() {
        robot
            .startTest()
            .injectUsersErrorNoConnectionMock()
            .launchFragment()
            .validateContainsErrorScreen()
            .validateNoConnectionMessageError()
            .endTest()
    }
    
    @Test
    fun getRepositoriesWithErrorAndGetLocalBase() {
        robot
            .startTest()
            .injectUsersErrorNoConnectionMock()
            .injectRepositoriesDatabase()
            .launchFragment()
            .validateContainsLocalData()
            .validateContainsMessageHeaderError()
            .endTest()
    }
    
    @Test
    fun getRepositoriesWithPagination() {
        robot
            .startTest()
            .injectUsersSuccessMock()
            .injectUsersSuccessPage2Mock()
            .launchFragment()
            .scrollToEndRecyclerView()
            .scrollToBottom()
            .validateRepositoriesPage2Screen()
            .endTest()
    }
    
    @Test
    fun getRepositoriesWithErrorInPagination() {
        robot
            .startTest()
            .injectUsersSuccessMock()
            .injectUsersErrorServerMock()
            .launchFragment()
            .scrollToEndRecyclerView()
            .scrollToBottom()
            .validateFooterError()
            .endTest()
    }
    
    @Test
    fun getRepositoriesAndTapOnItem() {
        robot
            .startTest()
            .injectUsersSuccessMock()
            .launchFragment()
            .tapOnItem()
            .endTest()
    }
}