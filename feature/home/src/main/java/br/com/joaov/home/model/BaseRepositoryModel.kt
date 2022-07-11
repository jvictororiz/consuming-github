package br.com.joaov.home.model

internal data class BaseRepositoryModel(
    val isLocal: Boolean = false,
    val totalCount: Int = 0,
    val items: List<RepositoryModel> = mutableListOf()
)