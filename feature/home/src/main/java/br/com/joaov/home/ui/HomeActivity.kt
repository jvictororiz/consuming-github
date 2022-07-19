package br.com.joaov.home.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.joaov.home.R
import br.com.joaov.home.di.homeModules
import br.com.joaov.network.di.networkModule
import br.com.joaov.persistence.di.persistenceModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

internal class HomeActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        loadKoinModules(networkModule)
        loadKoinModules(homeModules)
        loadKoinModules(persistenceModule)
    }
    
    override fun onDestroy() {
        unloadKoinModules(homeModules)
        unloadKoinModules(networkModule)
        unloadKoinModules(persistenceModule)
        super.onDestroy()
    }
}