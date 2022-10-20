package com.leonardo.pokedexapp

import androidx.room.Room
import com.leonardo.pokedexapp.database.PokemonRoomDatabase
import com.leonardo.pokedexapp.database.daos.PokemonDao
import com.leonardo.pokedexapp.repositories.PokemonFavoritesRepository
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import com.leonardo.pokedexapp.retrofitservice.RetrofitService
import com.leonardo.pokedexapp.viewmodel.PokemonFavoritesViewModel
import com.leonardo.pokedexapp.viewmodel.PokemonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.inject
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    single {
        Room.databaseBuilder(
            get(),
            PokemonRoomDatabase::class.java, "pokemon_table"
        ).build()

    }

    single { get<PokemonRoomDatabase>().pokemonDao() }

    single {
        PokemonsRepository(get())
    }

    single {
        PokemonFavoritesRepository(get())
    }


    viewModel {
        PokemonViewModel(get())
    }

    viewModel {
        PokemonFavoritesViewModel(get())
    }

}