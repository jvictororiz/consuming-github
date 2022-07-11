package br.com.joaov.home.data.repository.mapper

import br.com.joaov.home.model.OwnerModel
import br.com.joaov.home.model.RepositoryModel
import br.com.joaov.persistence.data.entity.OwnerEntity
import br.com.joaov.persistence.data.entity.RepositoryEntity


internal fun RepositoryEntity.toRepositoryModel() = RepositoryModel(
    id = id,
    name = name,
    fullName = fullName,
    stargazersCount = stargazersCount,
    url = url,
    forksCount = forksCount,
    owner = owner.toOwnerModel()
)

internal fun List<RepositoryModel>.toListRepositoryEntity(page: Int) = map { it.toRepositoryEntity(page) }

internal fun RepositoryModel.toRepositoryEntity(page: Int) = RepositoryEntity(
    id = id,
    name = name,
    fullName = fullName,
    page = page,
    stargazersCount = stargazersCount,
    url = url,
    forksCount = forksCount,
    owner = owner.toOwnerEntity()
)

internal fun List<RepositoryEntity>.toListRepositoryModel() = map { it.toRepositoryModel() }

internal fun OwnerEntity.toOwnerModel() = OwnerModel(
    id = idUser,
    login = login,
    avatarUrl = avatarUrl,
    urlRepository = urlRepository
)

internal fun OwnerModel.toOwnerEntity() = OwnerEntity(
    idUser = id,
    login = login,
    avatarUrl = avatarUrl,
    urlRepository = urlRepository
)