package com.leonardo.pokedexapp.model.responsemodel

data class PokemonTypesResponse(
    val damage_relations: DamageRelations,
    val game_indices: List<GameIndice>,
    val generation: GenerationX,
    val id: Int,
    val move_damage_class: MoveDamageClass,
    val moves: List<Move>,
    val name: String,
    val names: List<Name>,
    val past_damage_relations: List<PastDamageRelation>,
    val pokemon: List<PokemonR>
)