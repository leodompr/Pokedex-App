package com.leonardo.pokedexapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonUiModel(
    val name: String = "",
    val imageUrl: String = "",
    val type : String = "",
    val weight : String = "",
    val height : String = "",
) : Parcelable {
    fun mapFrom(pokemon: Pokemon): PokemonUiModel {
        return PokemonUiModel(
            name = pokemon.name ?: "",
            imageUrl = pokemon.url.getPicUrl() ?: "",
        )
    }

    fun mapFrom2(pokemon: PokemonDetails): PokemonUiModel {
        return PokemonUiModel(
            name = pokemon.name ?: "",
            imageUrl = pokemon.sprites.frontDefault ?: "",
            type = pokemon.types.first().type.name,
            weight = pokemon.weight.toString(),
            height = pokemon.height.toString()
        )
    }

    fun String.extractId() = this.substringAfter("pokemon").replace("/", "").toInt()

    fun String.getPicUrl(): String {
        val id = this.extractId()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
    }

}