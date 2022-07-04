package com.leonardo.pokedexapp.repositories

import androidx.annotation.WorkerThread
import com.leonardo.pokedexapp.database.daos.PokemonDao
import com.leonardo.pokedexapp.model.PokemonDaoModel
import kotlinx.coroutines.flow.Flow

class PokemonFavoritesRepository(private val pokemonFavoritesDao: PokemonDao) {

    val allPokemonFavorites: Flow<List<PokemonDaoModel>> = pokemonFavoritesDao.getAllFavorites()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(pokemonFavorites: PokemonDaoModel) {
        pokemonFavoritesDao.insertFavorites(pokemonFavorites)
    }

    suspend fun delete(pokemonFavorites: PokemonDaoModel) {
        pokemonFavoritesDao.removeFavorites(pokemonFavorites)
    }
}
