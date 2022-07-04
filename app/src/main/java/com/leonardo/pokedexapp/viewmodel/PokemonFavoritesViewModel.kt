package com.leonardo.pokedexapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.leonardo.pokedexapp.model.PokemonDaoModel
import com.leonardo.pokedexapp.repositories.PokemonFavoritesRepository
import kotlinx.coroutines.launch

class PokemonFavoritesViewModel(private val repository: PokemonFavoritesRepository) : ViewModel() {

    val allFavorites: LiveData<List<PokemonDaoModel>> = repository.allPokemonFavorites.asLiveData()

    fun insert(pokemon: PokemonDaoModel) = viewModelScope.launch {
        repository.insert(pokemon)
    }

    fun delete(pokemon: PokemonDaoModel) = viewModelScope.launch {
        repository.delete(pokemon)
    }

}