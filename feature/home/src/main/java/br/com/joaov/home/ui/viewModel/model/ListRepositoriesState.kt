package br.com.joaov.home.ui.viewModel.model

import br.com.joaov.home.R
import br.com.joaov.home.model.RepositoryModel

internal data class ListRepositoriesState(
    var showScreenWithoutConnection: Boolean = false,
    var messageFooter: Int = R.string.message_error_default,
    var loading: Boolean = false,
    var showList: Boolean = false,
    var showFooterPaginationError: Boolean = false,
    var showTextWithoutConnection: Boolean = false,
    var listRepositories: MutableList<RepositoryModel> = mutableListOf()
) {
    
    fun showScreenLoading() = this.apply {
        loading = true
        showList = false
        showScreenWithoutConnection = false
    }
    
    fun showPaginationLoading() = this.apply {
        loading = true
        showList = true
        messageFooter = R.string.message_error_default
        showFooterPaginationError = false
        showScreenWithoutConnection = false
        showTextWithoutConnection = false
    }
    
    fun stopLoading() = this.apply {
        showScreenWithoutConnection = false
        loading = false
    }
    
    fun showScreenError(emptyItems: Boolean, footerError: Int = R.string.without_connection_internet ) = this.apply {
        loading = false
        showFooterPaginationError = !emptyItems
        showList = !emptyItems
        showScreenWithoutConnection = emptyItems
        messageFooter = footerError
    }
    
    fun showList(textWithoutConnection: Boolean, list: MutableList<RepositoryModel>) = this.apply {
        loading = false
        showTextWithoutConnection = textWithoutConnection
        showScreenWithoutConnection = false
        showFooterPaginationError = false
        showList = true
        listRepositories = listRepositories.plus(list).toMutableList()
    }
}