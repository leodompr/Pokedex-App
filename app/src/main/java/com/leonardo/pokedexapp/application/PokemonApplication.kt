package com.leonardo.pokedexapp.application

import android.app.Application
import com.leonardo.pokedexapp.appModule
import com.leonardo.pokedexapp.database.PokemonRoomDatabase
import com.leonardo.pokedexapp.repositories.PokemonFavoritesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PokemonApplication)
            modules(appModule)
        }
    }

    val database by lazy { PokemonRoomDatabase.getDataBase(this) }
     //Garante que o repositorio sera instanciado apenas uma vez
}