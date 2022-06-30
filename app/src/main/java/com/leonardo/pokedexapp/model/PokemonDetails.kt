package com.leonardo.pokedexapp.model

import com.google.gson.annotations.SerializedName

data class PokemonDetails(
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("types")
    val types: List<TypePokemon>,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("sprites")
    val sprites: Sprites
)
