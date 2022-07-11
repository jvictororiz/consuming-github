package br.com.joaov.home.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.joaov.home.domain.exception.InternetConnectionException
import br.com.joaov.home.domain.exception.MultiplesRequestException
import br.com.joaov.home.domain.usecase.GetRepositoriesUseCase
import br.com.joaov.home.model.BaseRepositoryModel
import br.com.joaov.home.model.OwnerModel
import br.com.joaov.home.model.RepositoryModel
import br.com.joaov.home.ui.viewModel.model.ListRepositoriesEvent
import br.com.joaov.home.ui.viewModel.model.ListRepositoriesState
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException


@RunWith(JUnit4::class)
internal class ListRepositoriesViewModelTest {
    
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    
    private val useCase: GetRepositoriesUseCase = mockk(relaxed = true)
    private var state = mockk<Observer<ListRepositoriesState>>(relaxed = true)
    private var event = mockk<Observer<ListRepositoriesEvent>>(relaxed = true)
    private lateinit var viewModel: ListRepositoriesViewModel
    
    @Test
    fun `when tapOnItem then call GoToWeb with item url`() {
        val expected = getMockListRepositories()
        val clickItem = expected.items[0]
        coEvery { useCase.invoke(any()) } answers { Result.success(expected) }
        
        prepareViewModel()
        viewModel.tapOnItem(clickItem)
    
        val initState = ListRepositoriesState()
        
        
        coVerifySequence {
            state.onChanged(initState.showScreenLoading())
            state.onChanged(initState.showList(false, expected.items.toMutableList()))
        }
    
        coVerifySequence {
            event.onChanged(ListRepositoriesEvent.GoToWeb(clickItem.url))
        }
    }
    @Test
    fun `when tapOnUserItem then call GoToWeb with user repository url`() {
        val expected = getMockListRepositories()
        val clickItem = expected.items[0]
        coEvery { useCase.invoke(any()) } answers { Result.success(expected) }
        
        prepareViewModel()
        viewModel.tapOnUserItem(clickItem)
    
        val initState = ListRepositoriesState()
    
        coVerifySequence {
            state.onChanged(initState.showScreenLoading())
            state.onChanged(initState.showList(false, expected.items.toMutableList()))
        }
    
        coVerifySequence {
            event.onChanged(ListRepositoriesEvent.GoToWeb(clickItem.owner.urlRepository))
        }
    }
    
    @Test
    fun `when loadNewPage with success without cache then show list`() {
        val expected = getMockListRepositories()
        coEvery { useCase.invoke(any()) } answers { Result.success(expected) }
        
        prepareViewModel()
        
        val initState = ListRepositoriesState()
    
        coVerifySequence {
            state.onChanged(initState.showScreenLoading())
            state.onChanged(initState.showList(false, expected.items.toMutableList()))
        }
    }
    
    @Test
    fun `when loadNewPage no internet but with cache then show list and message cache`() {
        val expected = getMockListRepositories(true)
        coEvery { useCase.invoke(any()) } answers { Result.success(expected) }
        
        prepareViewModel()
        
        val initState = ListRepositoriesState()
    
        coVerifySequence {
            state.onChanged(initState.showScreenLoading())
            state.onChanged(initState.showList(true, expected.items.toMutableList()))
        }
    }
    
    @Test
    fun `when loadNewPage no internet no cache on first page then show showScreenError`() {
        val expected = InternetConnectionException()
        coEvery { useCase.invoke(any()) } answers { Result.failure(expected) }
        
        prepareViewModel()
        
        val initState = ListRepositoriesState()
    
        coVerifySequence {
            state.onChanged(initState.showScreenLoading())
            state.onChanged(initState.showScreenError(true))
        }
    }
    
    @Test
    fun `when loadNewPage with internet after no internet then show paginationError`()  {
        val expected = getMockListRepositories()
        coEvery { useCase.invoke(any()) } answers { Result.success(expected) } andThenAnswer { Result.failure(InternetConnectionException()) }
        
        prepareViewModel()
        viewModel.loadNewPage()
        
        val initState = ListRepositoriesState()
    
        coVerifySequence {
            state.onChanged(initState.showScreenLoading())
            state.onChanged(initState.showList(false, expected.items.toMutableList()))
            state.onChanged(initState.showPaginationLoading())
            state.onChanged(initState.showScreenError(false))
        }
    }
    
    @Test
    fun `when loadNewPage with multiplesRequestException then show message error`()  {
        coEvery { useCase.invoke(any()) } answers { Result.failure(MultiplesRequestException()) }
        
        prepareViewModel()
        
        val initState = ListRepositoriesState()
    
        coVerifySequence {
            state.onChanged(initState.showScreenLoading())
            state.onChanged(initState.stopLoading())
        }
    
        coVerifySequence {
            event.onChanged(ListRepositoriesEvent.ShowMessage(2131624017))
        }
    }
    
    private fun getMockListRepositories(isLocal: Boolean = false) = BaseRepositoryModel(
        isLocal = isLocal,
        totalCount = 1000,
        items = listOf(
            RepositoryModel(1, "joao", "github", "joao/repo", 1224, 4325, OwnerModel(1, "joao", "teste", "teste")),
            RepositoryModel(2, "pedro", "github", "pedro/repo", 1224, 4325, OwnerModel(1, "joao", "teste", "teste")),
            RepositoryModel(3, "matheus", "github", "matheus/repo", 1224, 4325, OwnerModel(1, "joao", "teste", "teste")),
            RepositoryModel(4, "victor", "github", "victor/repo", 1224, 4325, OwnerModel(1, "joao", "teste", "teste")),
        )
    )
    
    private fun prepareViewModel() {
        viewModel = ListRepositoriesViewModel(useCase)
        viewModel.eventLiveData.observeForever(event)
        viewModel.stateLiveData.observeForever(state)
    }
    
}