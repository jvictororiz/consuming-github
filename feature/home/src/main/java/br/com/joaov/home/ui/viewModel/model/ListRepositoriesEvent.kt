package br.com.joaov.home.ui.viewModel.model

internal sealed class ListRepositoriesEvent {
    data class GoToWeb(val url: String) : ListRepositoriesEvent()
    data class ShowMessage(val idMessage: Int) : ListRepositoriesEvent()
}