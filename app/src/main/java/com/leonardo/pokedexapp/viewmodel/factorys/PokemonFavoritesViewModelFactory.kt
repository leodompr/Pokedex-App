package com.leonardo.pokedexapp.viewmodel.factorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leonardo.pokedexapp.repositories.PokemonFavoritesRepository
import com.leonardo.pokedexapp.viewmodel.PokemonFavoritesViewModel

class PokemonFavoritesViewModelFactory(private val repository: PokemonFavoritesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonFavoritesViewModel::class.java)) {
            PokemonFavoritesViewModel(this.repository) as T
        } else {
            throw  IllegalArgumentException("ViewModel Not Found")
        }
    }
}