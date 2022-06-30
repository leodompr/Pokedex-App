package com.leonardo.pokedexapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leonardo.pokedexapp.databinding.FragmentHomeBinding
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import com.leonardo.pokedexapp.retrofitservice.RetrofitService
import com.leonardo.pokedexapp.ui.adapters.HomeFragmentListAdapter
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModel
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModelFactory


class HomeFragment : Fragment() {
    private val retrofitService = RetrofitService.getInstance()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var layoutManagerRv: GridLayoutManager
    private val adapterRv = HomeFragmentListAdapter{
        navToDetail(it)
    }
    private lateinit var viewModel: HomePockemonViewModel
    var listPokemon : MutableList<PokemonUiModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            HomePockemonViewModelFactory(PokemonsRepository(retrofitService))
        )[HomePockemonViewModel::class.java] //ViewModel

    }

    override fun onResume() {
        super.onResume()
        viewModel.getPokemons()
        initiRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pokemonList.observe(requireActivity()) {

            if (listPokemon.isEmpty()){
                for (pokemon in it.results) {
                    viewModel.getPokemonsDetails(pokemon.name)
                }
            }

        }

        viewModel.pokemonDetails.observe(requireActivity()) {
            if (listPokemon.contains(PokemonUiModel().mapFrom2(it))) {
                Log.d("HomeFragment", "Pokemon already in list")
            } else {
                listPokemon.add(PokemonUiModel().mapFrom2(it))

            }
//            if (offset < 1154) {
//                offset++
//            }
            adapterRv.setDataSet(listPokemon)
            adapterRv.notifyDataSetChanged()


        }


    }

    private fun navToDetail(pokemon: PokemonUiModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsPokemonFragment(pokemon)
        findNavController().navigate(action)
    }


    fun initiRecyclerView() {
        adapterRv.setDataSet(listPokemon)
        layoutManagerRv = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.rvPokemonList.apply {
            layoutManager = layoutManagerRv
            adapter = adapterRv
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}