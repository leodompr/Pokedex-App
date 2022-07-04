package com.leonardo.pokedexapp.ui.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.application.PokemonApplication
import com.leonardo.pokedexapp.databinding.FragmentFavoritesBinding
import com.leonardo.pokedexapp.model.PokemonDaoModel
import com.leonardo.pokedexapp.ui.adapters.FavoritesPokemonAdapter
import com.leonardo.pokedexapp.viewmodel.PokemonFavoritesViewModel
import com.leonardo.pokedexapp.viewmodel.factorys.PokemonFavoritesViewModelFactory


class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    val adapterRv = FavoritesPokemonAdapter { navToDetail(it) }
    private val viewModel: PokemonFavoritesViewModel by viewModels {
        PokemonFavoritesViewModelFactory((requireActivity().application as PokemonApplication).repository)
    }
    private var favoritesList = mutableListOf<PokemonDaoModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.allFavorites.observe(this, Observer {
            it?.let {
                favoritesList = it.toMutableList()
                println(favoritesList)
            }
            adapterRv.setDataSet(favoritesList)
            adapterRv.notifyDataSetChanged()
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiRecyclerView()

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarPokemonsFavorites)

        (activity as AppCompatActivity?)!!.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.title_favorites_fragment)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            binding.toolbarPokemonsFavorites.setTitleTextColor(resources.getColor(R.color.white))
            binding.toolbarPokemonsFavorites.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        colorStatusBar()

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())

            }
        })

    }

    override fun onResume() {
        super.onResume()
        initiRecyclerView()
    }


    private fun colorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#348BEE")
        }
    }


    private fun initiRecyclerView() {
        binding.rvPokemonFavorites.apply {
            adapter = adapterRv
        }
        adapterRv.setDataSet(favoritesList)
        adapterRv.notifyDataSetChanged()

    }

    private fun navToDetail(pokemonDaoModel: PokemonDaoModel) {
        val action =
            FavoritesFragmentDirections.actionFavoritesFragmentToDetailsPokemonFragment(
                pokemonDaoModel.name
            )
        findNavController().navigate(action)
    }

    private fun filter(text: String) {
        val listaFiltrada: MutableList<PokemonDaoModel> =
            mutableListOf()
        for (pokemon in favoritesList) {
            if (pokemon.name.uppercase().contains(text.uppercase())) {
                listaFiltrada.add(pokemon)
            }
        }
        adapterRv.filterList(listaFiltrada)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}