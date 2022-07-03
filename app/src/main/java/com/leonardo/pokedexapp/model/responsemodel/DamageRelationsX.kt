package com.leonardo.pokedexapp.model.responsemodel

data class DamageRelationsX(
    val double_damage_from: List<DoubleDamageFromX>,
    val double_damage_to: List<DoubleDamageToX>,
    val half_damage_from: List<HalfDamageFromX>,
    val half_damage_to: List<HalfDamageToX>,
    val no_damage_from: List<Any>,
    val no_damage_to: List<Any>
)