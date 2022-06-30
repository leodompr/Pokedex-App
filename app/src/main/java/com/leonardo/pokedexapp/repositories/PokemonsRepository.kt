package com.leonardo.pokedexapp.repositories

import com.leonardo.pokedexapp.retrofitservice.RetrofitService

class PokemonsRepository(private val retrofitService: RetrofitService) {
    suspend fun getPokemons() = retrofitService.getPokemonResponseList()
    suspend fun getPokemonDetails(name: String) = retrofitService.getPokemonDetails(name)

}
