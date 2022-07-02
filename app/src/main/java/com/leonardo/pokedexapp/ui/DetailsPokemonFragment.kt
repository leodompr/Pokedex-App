package com.leonardo.pokedexapp.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.databinding.FragmentDetailsPokemonBinding
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import com.leonardo.pokedexapp.retrofitservice.RetrofitService
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModel
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModelFactory


class DetailsPokemonFragment : Fragment() {
    private val retrofitService = RetrofitService.getInstance()
    private var _binding: FragmentDetailsPokemonBinding? = null
    private val binding get() = _binding!!
    private var pokemon: PokemonUiModel? = null
    val args: DetailsPokemonFragmentArgs by navArgs()
    private lateinit var viewModel: HomePockemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(
            requireActivity(),
            HomePockemonViewModelFactory(PokemonsRepository(retrofitService))
        )[HomePockemonViewModel::class.java]


        viewModel.getPokemon(args.pokemon.name.lowercase())

        viewModel.pokemon.observe(requireActivity()) {
            if (it != null) {
                pokemon = PokemonUiModel().pokemonDetaisToPokemonUiModel(it)

            }
        }


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

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarDetailsPokemon)

        (activity as AppCompatActivity?)!!.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            title = null
            binding.toolbarDetailsPokemon.setTitleTextColor(resources.getColor(R.color.white))
            binding.toolbarDetailsPokemon.setNavigationOnClickListener {
                viewModel.pokemon.postValue(null)
                requireActivity().onBackPressed()
            }
        }

        Glide.with(this)
            .load(R.drawable.pokeball)
            .into(binding.pokeballLoading)


        verifyPokemon()


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

        try {
            Glide.with(binding.imVPokemonDetail)
                .load(pokemon.imageUrl)
                .override(1000, 1000)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imVPokemonDetail)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Erro ao carregar Pokémon, verifique sua conexão.",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun loadingCancel() {
        if (pokemon != null) {
            binding.pokeballLoading.visibility = View.GONE
            binding.tvLoadingDetail.visibility = View.GONE
            binding.constraintLayoutDetailsPokemon.visibility = View.VISIBLE
            pokemon?.let {
                initView(it)
            }
        } else {
            verifyPokemon()

        }

    }

    private fun verifyPokemon() {
        when (pokemon == null) {
            true -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    getPokemonLoading()
                }, 1000)
            }
            false -> {
                loadingCancel()
            }
        }
    }

    private fun getPokemonLoading() {
        viewModel.getPokemon(args.pokemon.name.lowercase())
        loadingCancel()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.pokemon.postValue(null)
        _binding = null
    }


}