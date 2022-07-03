package com.leonardo.pokedexapp.model.responsemodel

import com.google.gson.annotations.SerializedName

data class TypePokemonType (
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)