package br.com.joaov.home.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaov.home.R
import br.com.joaov.home.domain.exception.InternetConnectionException
import br.com.joaov.home.domain.exception.MultiplesRequestException
import br.com.joaov.home.domain.usecase.GetRepositoriesUseCase
import br.com.joaov.home.model.BaseRepositoryModel
import br.com.joaov.home.model.RepositoryModel
import br.com.joaov.home.ui.viewModel.model.ListRepositoriesEvent
import br.com.joaov.home.ui.viewModel.model.ListRepositoriesState
import br.com.joaov.util.livedata.MultipleLiveState
import br.com.joaov.util.livedata.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class ListRepositoriesViewModel(
    private val useCase: GetRepositoriesUseCase
) : ViewModel() {
    
    private val _stateLiveData = MultipleLiveState<ListRepositoriesState>()
    val stateLiveData: LiveData<ListRepositoriesState> get() = _stateLiveData
    
    private val _eventLiveData = SingleLiveEvent<ListRepositoriesEvent>()
    val eventLiveData: LiveData<ListRepositoriesEvent> get() = _eventLiveData
    
    private var page = 0
    
    init {
        runState { it.showScreenLoading() }
        newPage()
    }
    
    fun tapOnItem(repositoryModel: RepositoryModel) {
        ListRepositoriesEvent.GoToWeb(repositoryModel.url).run()
    }
    
    fun tapOnUserItem(repositoryModel: RepositoryModel) {
        ListRepositoriesEvent.GoToWeb(repositoryModel.owner.urlRepository).run()
    }
    
    fun loadNewPage() {
        if (page == LAST_PAGE) return
        runState { it.showPaginationLoading() }
        newPage()
    }
    
    private fun newPage() = viewModelScope.launch(Dispatchers.IO) {
        page++
        val result = useCase(page)
        
        result.onSuccess {
            val response = result.getOrDefault(BaseRepositoryModel())
            runState { it.showList(response.isLocal, response.items.toMutableList()) }
        }.onFailure { error ->
            treatmentError(error)
        }
    }
    
    private fun treatmentError(error: Throwable) {
        page--
        when (error) {
            is InternetConnectionException -> {
                val emptyItems = stateLiveData.value?.listRepositories.isNullOrEmpty()
                runState { it.showScreenError(emptyItems) }
            }
            is MultiplesRequestException -> {
                runState { it.stopLoading() }
                ListRepositoriesEvent.ShowMessage(R.string.message_error_multiples_requests).run()
            }
            else -> {
                val emptyItems = stateLiveData.value?.listRepositories.isNullOrEmpty()
                runState { it.showScreenError(emptyItems, R.string.message_error_default) }
            }
        }
    }
    
    
    private fun runState(newState: (ListRepositoriesState) -> ListRepositoriesState) {
        _stateLiveData.postValue(newState(stateLiveData.value ?: ListRepositoriesState()))
    }
    
    private fun ListRepositoriesEvent.run() {
        _eventLiveData.postValue(this)
    }
    
    companion object {
        private const val LAST_PAGE = 30
    }
}