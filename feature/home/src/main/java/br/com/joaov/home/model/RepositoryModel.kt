package br.com.joaov.home.model

internal data class RepositoryModel(
    val id: Int,
    val name: String,
    val url: String,
    val fullName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val owner: OwnerModel
)
