package br.com.joaov.home.domain.usecase

import br.com.joaov.home.data.repository.contract.GithubRepository
import br.com.joaov.home.model.BaseRepositoryModel

internal class GetRepositoriesUseCase(
    private val repository: GithubRepository
) {
    
    suspend operator fun invoke(page: Int): Result<BaseRepositoryModel> {
        return repository.getRepositoryByPage(page)
    }
}