package br.com.joaov.home.data.repository

import android.util.Log
import br.com.joaov.home.data.repository.contract.GithubRepository
import br.com.joaov.home.data.repository.mapper.toBaseRepositoryModel
import br.com.joaov.home.data.repository.mapper.toListRepositoryEntity
import br.com.joaov.home.data.repository.mapper.toListRepositoryModel
import br.com.joaov.home.data.service.RepositoryService
import br.com.joaov.home.domain.exception.InternetConnectionException
import br.com.joaov.home.domain.exception.MultiplesRequestException
import br.com.joaov.home.model.BaseRepositoryModel
import br.com.joaov.network.domain.exception.ServerException
import br.com.joaov.persistence.data.dao.RepositoryDao
import retrofit2.HttpException
import java.io.IOException

internal class GithubRepositoryImpl(
    private val repositoryService: RepositoryService,
    private val repositoryDao: RepositoryDao
) : GithubRepository {
    
    override suspend fun getRepositoryByPage(page: Int): Result<BaseRepositoryModel> {
        return try {
            val response = repositoryService.getAllRepositories(page).toBaseRepositoryModel(false)
            repositoryDao.saveRepositories(response.items.toListRepositoryEntity(page))
            Result.success(response)
        } catch (exception: Exception) {
            Log.i("joao_victor",exception.message.toString())
            exception.printStackTrace()
            println("joao_victor$exception")
            when {
                exception is IOException -> {
                    val localData = repositoryDao.getRepositoriesByPage(page).toListRepositoryModel()
                    if (localData.isEmpty()) {
                        Result.failure(InternetConnectionException())
                    } else {
                        Result.success(BaseRepositoryModel(isLocal = true, items = localData))
                    }
                }
                exception is HttpException && exception.code() == 403 -> Result.failure(MultiplesRequestException())
                else -> Result.failure(ServerException(exception.message))
            }
        }
    }
}