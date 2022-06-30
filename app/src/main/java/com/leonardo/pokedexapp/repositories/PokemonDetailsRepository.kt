package com.leonardo.pokedexapp.repositories

import com.leonardo.pokedexapp.retrofitservice.RetrofitService

class PokemonDetailsRepository(private val retrofitService: RetrofitService) {
    suspend fun getPokemonDetails(name: String) = retrofitService.getPokemonDetails(name)
}
