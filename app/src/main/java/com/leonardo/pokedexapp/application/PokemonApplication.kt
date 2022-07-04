package com.leonardo.pokedexapp.application

import android.app.Application
import com.leonardo.pokedexapp.database.PokemonRoomDatabase
import com.leonardo.pokedexapp.repositories.PokemonFavoritesRepository

class PokemonApplication : Application() {

    val database by lazy { PokemonRoomDatabase.getDataBase(this) }
    val repository by lazy { PokemonFavoritesRepository(database.pokemonDao()) }
}