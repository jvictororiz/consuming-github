package br.com.joaov.home.data.service

import br.com.joaov.home.data.model.BaseRepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RepositoryService {
    @GET(GET_REPOSITORIES_URL)
    suspend fun getAllRepositories(
        @Query("page") page: Int,
        @Query("sort") sort: String = "stars",
        @Query("q", encoded = true) language: String = "language:kotlin"
    ): BaseRepositoryResponse
    
    companion object {
        private const val GET_REPOSITORIES_URL = "/search/repositories"
    }
}