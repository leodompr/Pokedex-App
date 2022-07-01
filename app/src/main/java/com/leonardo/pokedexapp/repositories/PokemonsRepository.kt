package com.leonardo.pokedexapp.repositories

import com.leonardo.pokedexapp.retrofitservice.RetrofitService

class PokemonsRepository(private val retrofitService: RetrofitService) {
     fun getPokemons(page : Int) = retrofitService.getPokemonResponseList(offset = page)
     fun getPokemonDetails(name: String) = retrofitService.getPokemonDetails(name)

}
