package br.com.joaov.home.data.repository.mapper

import br.com.joaov.home.data.model.BaseRepositoryResponse
import br.com.joaov.home.data.model.OwnerResponse
import br.com.joaov.home.data.model.RepositoryResponse
import br.com.joaov.home.model.BaseRepositoryModel
import br.com.joaov.home.model.OwnerModel
import br.com.joaov.home.model.RepositoryModel


internal fun BaseRepositoryResponse.toBaseRepositoryModel(isLocal: Boolean) = BaseRepositoryModel(
    totalCount = totalCount ?: 0,
    isLocal = isLocal,
    items = items.map { it.toRepositoryModel() }
)

internal fun RepositoryResponse.toRepositoryModel() = RepositoryModel(
    id = id,
    name = name,
    fullName = fullName,
    stargazersCount = stargazersCount,
    forksCount = forksCount,
    url = url,
    owner = owner.toOwnerModel()
)

internal fun OwnerResponse.toOwnerModel() = OwnerModel(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    urlRepository = urlRepository
)