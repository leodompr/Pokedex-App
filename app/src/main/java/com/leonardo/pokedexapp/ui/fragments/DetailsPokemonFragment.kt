package com.leonardo.pokedexapp.ui.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.application.PokemonApplication
import com.leonardo.pokedexapp.databinding.FragmentDetailsPokemonBinding
import com.leonardo.pokedexapp.model.PokemonDaoModel
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.model.responsemodel.PokemonDetails
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import com.leonardo.pokedexapp.retrofitservice.RetrofitService
import com.leonardo.pokedexapp.viewmodel.PokemonFavoritesViewModel
import com.leonardo.pokedexapp.viewmodel.PokemonViewModel
import com.leonardo.pokedexapp.viewmodel.factorys.PokemonFavoritesViewModelFactory
import com.leonardo.pokedexapp.viewmodel.factorys.PokemonViewModelFactory


class DetailsPokemonFragment : Fragment() {
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var binding: FragmentDetailsPokemonBinding
    private var pokemon: PokemonUiModel? = null
    private var pokemonApi: PokemonDetails? = null
    private var pokemonDao = mutableListOf<PokemonDaoModel>()
    private val args: DetailsPokemonFragmentArgs by navArgs()
    private lateinit var viewModel: PokemonViewModel

    private val viewModelFavorites: PokemonFavoritesViewModel by viewModels {
        PokemonFavoritesViewModelFactory((requireActivity().application as PokemonApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(
            requireActivity(),
            PokemonViewModelFactory(PokemonsRepository(retrofitService))
        )[PokemonViewModel::class.java]


        viewModel.getPokemon(args.pokemonName.lowercase())

        viewModelFavorites.allFavorites.observe(requireActivity(), Observer {
            pokemonDao = it.toMutableList()
        })

        viewModel.pokemon.observe(requireActivity()) {
            if (it != null) {
                println(it)
                pokemonApi = it
                pokemon = PokemonUiModel().pokemonDetaisToPokemonUiModel(it)

            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsPokemonBinding.inflate(inflater, container, false)
        return binding.root
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

        binding.selectorFavoritePokemon.setOnClickListener {
            pokemonApi?.let {
                setFavorite()
            }

        }

        verifyPokemon()


    }

    private fun setFavorite() {
        pokemonApi?.let {
            if (pokemonDao.contains(PokemonDaoModel().pokemonDetaisToPokemonDaoModel(it))) {
                viewModelFavorites.delete(PokemonDaoModel().pokemonDetaisToPokemonDaoModel(it))
                binding.selectorFavoritePokemon.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                Log.d("DELETE", "DELETE")
            } else {
                viewModelFavorites.insert(PokemonDaoModel().pokemonDetaisToPokemonDaoModel(it))
                binding.selectorFavoritePokemon.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                Log.d("INSERT", "INSERT")
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
        binding.constraintPokemonVariant.setCardBackgroundColor(Color.parseColor(pokemon.color))
        binding.dividerPokemonDetails.setBackgroundColor(Color.parseColor(pokemon.color))


        pokemonApi?.let {
            if (pokemonDao.contains(PokemonDaoModel().pokemonDetaisToPokemonDaoModel(it))) {
                binding.selectorFavoritePokemon.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.selectorFavoritePokemon.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }


        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window: Window = requireActivity().window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.parseColor(pokemon.color)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        try {
            Glide.with(binding.imVPokemonDetail)
                .load(pokemon.imageUrl)
                .override(1000, 1000)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imVPokemonDetail)

            Glide.with(binding.imVPokemonVariant1)
                .load(pokemon.variant1)
                .override(1000, 1000)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imVPokemonVariant1)


            Glide.with(binding.imVPokemonVariant2)
                .load(pokemon.variant2)
                .override(1000, 1000)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imVPokemonVariant2)

        } catch (e: Exception) {
            e.printStackTrace()
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
        viewModel.getPokemon(args.pokemonName.lowercase())
        loadingCancel()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.pokemon.postValue(null)

    }


}