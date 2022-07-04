package com.leonardo.pokedexapp.viewmodel.factorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import com.leonardo.pokedexapp.viewmodel.PokemonViewModel

class PokemonViewModelFactory(private val repository: PokemonsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            PokemonViewModel(this.repository) as T
        } else {
            throw  IllegalArgumentException("ViewModel Not Found")
        }
    }
}