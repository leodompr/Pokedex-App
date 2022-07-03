package com.leonardo.pokedexapp.model.responsemodel

data class PokemonDetails(
    val height: Int,
    val id: Int,
    val name: String,
    val types: List<TypePokemon>,
    val weight: Int,
    val sprites: Sprites,
    )
