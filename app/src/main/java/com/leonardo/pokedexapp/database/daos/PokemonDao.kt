package com.leonardo.pokedexapp.database.daos

import androidx.room.*
import com.leonardo.pokedexapp.model.PokemonDaoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table")
    fun getAllFavorites(): Flow<List<PokemonDaoModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorites(pokemon: PokemonDaoModel)

    @Delete
    suspend fun removeFavorites(pokemon: PokemonDaoModel)




}