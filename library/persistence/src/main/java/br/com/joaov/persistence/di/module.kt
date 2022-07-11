package br.com.joaov.persistence.di

import androidx.room.Room
import br.com.joaov.persistence.data.AppDatabase
import org.koin.dsl.module

internal const val DATABASE_NAME = "AppDatabase"

val persistenceModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}