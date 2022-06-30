package com.leonardo.pokedexapp.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.databinding.FragmentDetailsPokemonBinding
import com.leonardo.pokedexapp.model.Pokemon
import com.leonardo.pokedexapp.model.PokemonDetails
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.repositories.PokemonDetailsRepository
import com.leonardo.pokedexapp.retrofitservice.RetrofitService
import com.leonardo.pokedexapp.viewmodel.PokemonDetailsViewModel
import com.leonardo.pokedexapp.viewmodel.PokemonDetailsViewModelFactory
import java.util.*


class DetailsPokemonFragment : Fragment() {
    private val retrofitService = RetrofitService.getInstance()
    private var _binding: FragmentDetailsPokemonBinding? = null
    private val binding get() = _binding!!
    private lateinit var pokemon: PokemonUiModel
    val args: DetailsPokemonFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemon = args.pokemon
            println(pokemon)
            initView(pokemon)


        }

    private fun initView(pokemon: PokemonUiModel) {
//        binding.tvWeightPokemonDetail.text = pokemon.weight.toString()
//        binding.tvSizePokemonDetail.text = pokemon.height.toString()

        binding.tvWeightPokemonDetail.setTextColor(Color.parseColor(returnColorPokemonType(pokemon)))
        binding.tvSizePokemonDetail.setTextColor(Color.parseColor(returnColorPokemonType(pokemon)))
        binding.constraintLayoutDetailsPokemon.setBackgroundColor(Color.parseColor(returnColorPokemonType(pokemon)))
        binding.dividerPokemonDetails.setBackgroundColor(Color.parseColor(returnColorPokemonType(pokemon)))

        Glide.with(binding.imVPokemonDetail)
            .load(pokemon.imageUrl)
            .override(1000, 1000)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imVPokemonDetail)
    }


    fun returnColorPokemonType(pokemon: PokemonUiModel): String {
        return when(pokemon.type.toLowerCase(Locale.ROOT)) {
            "normal" -> "#FFFFFF"
            "fire" -> "#EB9E64"
            "water" -> "#7EC1DD"
            "electric" -> "#F1D357"
//            "grass" -> TypeGrass
//            "ice" -> TypeIce
//            "fighting" -> TypeFighting
//            "poison" -> TypePoison
//            "ground" -> TypeGround
//            "flying" -> TypeFlying
//            "psychic" -> TypePsychic
//            "bug" -> TypeBug
//            "rock" -> TypeRock
//            "ghost" -> TypeGhost
//            "dragon" -> TypeDragon
//            "dark" -> TypeDark
//            "steel" -> TypeSteel
//            "fairy" -> TypeFairy
            else -> "#FFFFFF"
        }
    }

}