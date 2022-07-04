package com.leonardo.pokedexapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.leonardo.pokedexapp.database.daos.PokemonDao
import com.leonardo.pokedexapp.model.PokemonDaoModel

@Database(entities = [PokemonDaoModel::class], version = 1)
abstract class PokemonRoomDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {

        @Volatile
        private var INSTANCE: PokemonRoomDatabase? = null

        fun getDataBase(context: Context): PokemonRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instace = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonRoomDatabase::class.java,
                    "pokemon_table"
                ).build()
                INSTANCE = instace
                instace
            }
        }

    }


}