package com.leonardo.pokedexapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.leonardo.pokedexapp.databinding.FragmentDetailsPokemonBinding
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.retrofitservice.RetrofitService


class DetailsPokemonFragment : Fragment() {
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
        binding.tvNamePokemonDatail.text = pokemon.name
        binding.tvWeightPokemonDetail.text = pokemon.weight
        binding.tvSizePokemonDetail.text = pokemon.height
        binding.tvWeightPokemonDetail.setTextColor(Color.parseColor(pokemon.color))
        binding.tvSizePokemonDetail.setTextColor(Color.parseColor(pokemon.color))
        binding.constraintLayoutDetailsPokemon.setBackgroundColor(Color.parseColor(pokemon.color))
        binding.dividerPokemonDetails.setBackgroundColor(Color.parseColor(pokemon.color))

        Glide.with(binding.imVPokemonDetail)
            .load(pokemon.imageUrl)
            .override(1000, 1000)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imVPokemonDetail)
    }


}