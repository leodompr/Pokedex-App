package com.leonardo.pokedexapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leonardo.pokedexapp.model.responsemodel.PokemonDetails
import java.util.*

@Entity(tableName = "pokemon_table")
data class PokemonDaoModel(
    @ColumnInfo(name = "pokemon")
    @PrimaryKey val name: String = "",
    val imageUrl: String = "",
    val type: String = "",
    val weight: String = "",
    val height: String = "",
    val color: String = "",
    val variant1: String = "",
    val variant2: String = "",
) {

    fun pokemonDetaisToPokemonDaoModel(pokemon: PokemonDetails): PokemonDaoModel {
        return PokemonDaoModel(
            name = pokemon.name.replaceFirstChar { it.uppercase() } ?: "",
            imageUrl = pokemon.sprites.frontDefault ?: "",
            type = returnTypeInPT(pokemon),
            weight = pokemon.weight.div(10).toString() + " kg",
            height = pokemon.height.toDouble().div(10).toString() + " m",
            color = returnColorPokemonType(pokemon),
            variant1 = pokemon.sprites.frontShiny ?: "",
            variant2 = pokemon.sprites.backShiny ?: ""
        )
    }

    private fun returnColorPokemonType(pokemon: PokemonDetails): String {
        return when (pokemon.types.first().type.name.lowercase(Locale.ROOT)) {
            "normal" -> "#919AA2"
            "fire" -> "#EB9E64"
            "water" -> "#7EC1DD"
            "electric" -> "#F1D357"
            "grass" -> "#97BEAF"
            "ice" -> "#BCD0D9"
            "fighting" -> "#D6AB85"
            "poison" -> "#B697B7"
            "ground" -> "#AE9988"
            "flying" -> "#9DD2D2"
            "psychic" -> "#998BC0"
            "bug" -> "#C2CD7D"
            "rock" -> "#959595"
            "ghost" -> "#A5CBA6"
            "dragon" -> "#E5989F"
            "dark" -> "#9597BE"
            "steel" -> "#C0C0C0"
            "fairy" -> "#D3A7CC"
            else -> "#919AA2"
        }
    }

    private fun returnTypeInPT(pokemon: PokemonDetails): String {
        return when (pokemon.types.first().type.name.lowercase(Locale.ROOT)) {
            "normal" -> "Normal"
            "fire" -> "Fogo"
            "water" -> "Água"
            "electric" -> "Elétrico"
            "grass" -> "Planta"
            "ice" -> "Gelo"
            "fighting" -> "Lutador"
            "poison" -> "Veneno"
            "ground" -> "Terra"
            "flying" -> "Voador"
            "psychic" -> "Psíquico"
            "bug" -> "Inseto"
            "rock" -> "Rocha"
            "ghost" -> "Fantasma"
            "dragon" -> "Dragão"
            "dark" -> "Sombrio"
            "steel" -> "Aço"
            "fairy" -> "Fada"
            else -> "Normal"
        }


    }
}