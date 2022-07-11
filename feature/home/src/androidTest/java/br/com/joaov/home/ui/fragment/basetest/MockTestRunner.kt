package br.com.joaov.home.ui.fragment.basetest

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.room.Room
import androidx.test.runner.AndroidJUnitRunner
import br.com.joaov.home.di.homeModules
import br.com.joaov.network.di.getNetworkModule
import br.com.joaov.persistence.data.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.test.KoinTest

class MockTestRunner : AndroidJUnitRunner() {
    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }
    
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, BaseApplicationTest::class.java.name, context)
    }
}

class BaseApplicationTest : Application(), KoinTest {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplicationTest)
            modules(
                getNetworkModule("http://127.0.0.1:8888"),
                mockModule,
                homeModules
            )
        }
    }
}

val mockModule = module {
    factory {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "DATABASE_TEST"
        ).allowMainThreadQueries().build()
    }
}