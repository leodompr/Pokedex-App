package com.leonardo.pokedexapp.model.responsemodel

import com.google.gson.annotations.SerializedName

data class TypePokemon(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypePokemonType
)
