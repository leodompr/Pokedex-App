package com.leonardo.pokedexapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.leonardo.pokedexapp.model.responsemodel.Pokemon
import com.leonardo.pokedexapp.model.responsemodel.PokemonResponse
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import io.mockk.*
import org.junit.Rule
import org.junit.Test


class HomePockemonViewModelTest{
    @get:Rule //Rule para testar o ViewModel já que na JVM não tem a MainThread do android
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<PokemonsRepository>() //Repositorio do ViewModel mockado
    private val onDataLoadedObserver = mockk<Observer<PokemonResponse>>(relaxed = true) //Observer Mockado

    @Test
    fun `when view model fetches data then it should call the repository`() { //Verifica se o repositorio é chamado
        val viewModel = instantiateViewModel() //Inicializamos o ViewModel
        val listPokemon = listOf(Pokemon("1", "teste")) //Lista de pokemons
        val mockedList : List<PokemonResponse> = listOf(
            PokemonResponse(0, null, null, listPokemon),
            PokemonResponse(2, null, null, listPokemon) ) //Criamos uma lista para testar as chamadas

        
        coEvery {  repository.getPokemons()  } returns mockedList //Toda chamada para o getData retorna a lista mockada para testes

        viewModel.getPokemons() //Chama a função

        coVerify {  repository.getPokemons()  } //Verifica se o getData foi chamado
        verify { onDataLoadedObserver.onChanged(mockedList) } //Verifica se o observer esta com o valor corredo, que é a lista mockada
    }

    private fun instantiateViewModel(): HomePockemonViewModel {
        val viewModel = HomePockemonViewModel(repository)
        viewModel.pokemonList.observeForever(onDataLoadedObserver) //Instanciamos que o observer deve obsercar a variavel onDataLoadedObserver
        return viewModel
    }
}
