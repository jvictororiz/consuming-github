package br.com.joaov.home.di

import br.com.joaov.home.data.repository.GithubRepositoryImpl
import br.com.joaov.home.data.repository.contract.GithubRepository
import br.com.joaov.home.data.service.RepositoryService
import br.com.joaov.home.domain.usecase.GetRepositoriesUseCase
import br.com.joaov.home.ui.viewModel.ListRepositoriesViewModel
import br.com.joaov.persistence.data.AppDatabase
import br.com.joaov.persistence.data.dao.RepositoryDao
import br.com.joaov.persistence.di.persistenceModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModules = module {
    viewModel { ListRepositoriesViewModel(get()) }
    factory { GetRepositoriesUseCase(get()) }
    factory<GithubRepository> { GithubRepositoryImpl(get(), get()) }
    factory { get<AppDatabase>().repositoryDao() }
    factory { (get<Retrofit>().create(RepositoryService::class.java)) }
}