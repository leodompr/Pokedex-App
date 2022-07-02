package com.leonardo.pokedexapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardo.pokedexapp.model.PokemonDetails
import com.leonardo.pokedexapp.model.PokemonResponse
import com.leonardo.pokedexapp.model.PokemonTypesResponse
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePockemonViewModel(private val repository: PokemonsRepository) : ViewModel() {
    val pokemonList = MutableLiveData<PokemonResponse>()
    val errorMessage = MutableLiveData<String>()
    val pokemonDetails = MutableLiveData<PokemonDetails>()
    val pokemon = MutableLiveData<PokemonDetails>()
    val pokemonByTypes = MutableLiveData<PokemonTypesResponse>()



     fun getPokemons(page : Int) = viewModelScope.launch{
        val request = repository.getPokemons(page = page)
        request.enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                //Quando houver resposta
                pokemonList.postValue(response.body())
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                //Quando houver falha
                errorMessage.postValue(t.message)
            }

        })
    }




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

    fun getPokemon(name: String) = viewModelScope.launch{
        val request = repository.getPokemon(name)
        request.enqueue(object : Callback<PokemonDetails> {
            override fun onResponse(call: Call<PokemonDetails>, response: Response<PokemonDetails>) {
                //Quando houver resposta
                pokemon.postValue(response.body())
            }

            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                //Quando houver falha
                errorMessage.postValue(t.message)
            }

        })
    }


    fun getPokemonsByTypes(id: String) = viewModelScope.launch{
        val request = repository.getPokemonsByTypes(id)
        request.enqueue(object : Callback<PokemonTypesResponse> {
            override fun onResponse(call: Call<PokemonTypesResponse>, response: Response<PokemonTypesResponse>) {
                //Quando houver resposta
                pokemonByTypes.postValue(response.body())
            }

            override fun onFailure(call: Call<PokemonTypesResponse>, t: Throwable) {
                //Quando houver falha
                errorMessage.postValue(t.message)
            }

        })
    }


}