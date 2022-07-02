package com.leonardo.pokedexapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.databinding.FragmentDetailsPokemonBinding
import com.leonardo.pokedexapp.model.PokemonUiModel


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
        initView(pokemon)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarDetailsPokemon)

        (activity as AppCompatActivity?)!!.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            title = null
            binding.toolbarDetailsPokemon.setTitleTextColor(resources.getColor(R.color.white))
            binding.toolbarDetailsPokemon.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }


    }

    private fun initView(pokemon: PokemonUiModel) {
        binding.tvNamePokemonDatail.text = pokemon.name
        binding.tvWeightPokemonDetail.text = pokemon.weight
        binding.tvSizePokemonDetail.text = pokemon.height
        binding.tvTypePokemonDetails.text = pokemon.type
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