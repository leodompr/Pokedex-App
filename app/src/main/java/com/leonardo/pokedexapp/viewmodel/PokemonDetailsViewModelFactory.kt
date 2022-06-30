package com.leonardo.pokedexapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leonardo.pokedexapp.repositories.PokemonDetailsRepository
import com.leonardo.pokedexapp.repositories.PokemonsRepository

class PokemonDetailsViewModelFactory(private val repository: PokemonDetailsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)) {
            PokemonDetailsViewModel(this.repository) as T
        } else {
            throw  IllegalArgumentException("ViewModel Not Found")
        }
    }
}