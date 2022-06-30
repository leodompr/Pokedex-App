package com.leonardo.pokedexapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardo.pokedexapp.model.PokemonDetails
import com.leonardo.pokedexapp.model.PokemonResponse
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.repositories.PokemonDetailsRepository
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsViewModel (private val repository: PokemonDetailsRepository) : ViewModel() {
    val pokemonDetails = MutableLiveData<PokemonDetails>()
    val errorMessage = MutableLiveData<String>()




    fun getPokemonsDetails(name: String) = viewModelScope.launch{
        val request = repository.getPokemonDetails(name)
        request.enqueue(object : Callback<PokemonDetails> {
            override fun onResponse(call: Call<PokemonDetails>, response: Response<PokemonDetails>) {
                //Quando houver resposta
                pokemonDetails.postValue(response.body())
            }

            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                //Quando houver falha
                errorMessage.postValue(t.message)
            }

        })
    }
}