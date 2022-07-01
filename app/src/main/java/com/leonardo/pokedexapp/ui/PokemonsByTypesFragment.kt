package com.leonardo.pokedexapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.leonardo.pokedexapp.databinding.FragmentPokemonsByTypesBinding
import com.leonardo.pokedexapp.model.Pokemon
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import com.leonardo.pokedexapp.retrofitservice.RetrofitService
import com.leonardo.pokedexapp.ui.adapters.HomeFragmentListAdapter
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModel
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModelFactory


class PokemonsByTypesFragment : Fragment() {
    private val retrofitService = RetrofitService.getInstance()
    private var _binding: FragmentPokemonsByTypesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomePockemonViewModel
    private var page: Int = 0
    private val adapterRv = HomeFragmentListAdapter {}
    var listPokemon: MutableList<PokemonUiModel> = mutableListOf()
    var listPokemonApi: MutableList<Pokemon> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(
            requireActivity(),
            HomePockemonViewModelFactory(PokemonsRepository(retrofitService))
        )[HomePockemonViewModel::class.java]

        viewModel.pokemonList.observe(requireActivity()) {
            for (pokemon in it.results) {
                if (listPokemonApi.contains(pokemon)) {
                    continue
                } else {
                    viewModel.getPokemonsDetails(pokemon.name)
                }
            }
        }


        val count = listOf(1, 2, 3, 4, 5 ,5 ,5 ,5 ,5 ,5 ,5)
        for (i in count) {
            viewModel.getPokemons(page)
            page++
        }

        viewModel.pokemonDetails.observe(requireActivity()) {

            try {
                if (listPokemon.contains(PokemonUiModel().pokemonDetaisToPokemonUiModel(it))) {
                    return@observe
                } else {
                    if (it.types.first().type.name == "fire") {
                        println(it.types)
                        listPokemon.add(PokemonUiModel().pokemonDetaisToPokemonUiModel(it))
                        adapterRv.notifyDataSetChanged()
                    }

                }
                adapterRv.setDataSet(listPokemon)
                adapterRv.notifyDataSetChanged()
            } catch (e: Exception) {
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonsByTypesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getPokemonsDetails("6")
        viewModel.getPokemons(page)

        binding.rvPokemonTypes.addOnScrollListener(object :
            androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = binding.rvPokemonTypes.childCount
                    val totalItemCount = binding.rvPokemonTypes.layoutManager?.itemCount ?: 0
                    val pastVisibleItems =
                        (binding.rvPokemonTypes.layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        page++
                        viewModel.getPokemons(page)
                    }
                }
            }
        })
    }


    private fun initiRecyclerView() {
        adapterRv.setDataSet(listPokemon)
        binding.rvPokemonTypes.apply {
            adapter = adapterRv
        }
    }

}