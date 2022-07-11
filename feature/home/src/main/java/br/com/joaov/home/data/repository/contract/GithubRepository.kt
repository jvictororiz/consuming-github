package br.com.joaov.home.data.repository.contract

import br.com.joaov.home.model.BaseRepositoryModel

internal interface GithubRepository {
    suspend fun getRepositoryByPage(page: Int): Result<BaseRepositoryModel>
}