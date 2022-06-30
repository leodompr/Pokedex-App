package com.leonardo.pokedexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.leonardo.pokedexapp.databinding.ActivityMainBinding
import com.leonardo.pokedexapp.databinding.FragmentHomeBinding
import com.leonardo.pokedexapp.model.Pokemon
import com.leonardo.pokedexapp.model.PokemonDetails
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.model.Sprites
import com.leonardo.pokedexapp.repositories.PokemonDetailsRepository
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import com.leonardo.pokedexapp.retrofitservice.RetrofitService
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModel
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModelFactory
import com.leonardo.pokedexapp.viewmodel.PokemonDetailsViewModel
import com.leonardo.pokedexapp.viewmodel.PokemonDetailsViewModelFactory

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}