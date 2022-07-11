package br.com.joaov.network.di

import br.com.joaov.network.BuildConfig
import br.com.joaov.network.data.retrofit
import org.koin.dsl.module

val networkModule = getNetworkModule()

fun getNetworkModule(baseUrl: String = BuildConfig.BASE_URL) = module {
    factory { retrofit(baseUrl) }
}