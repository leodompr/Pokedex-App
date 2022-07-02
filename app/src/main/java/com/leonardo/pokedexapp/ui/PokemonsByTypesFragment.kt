package com.leonardo.pokedexapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.databinding.FragmentPokemonsByTypesBinding
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.repositories.PokemonsRepository
import com.leonardo.pokedexapp.retrofitservice.RetrofitService
import com.leonardo.pokedexapp.ui.adapters.PokemonByTypesAdapter
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModel
import com.leonardo.pokedexapp.viewmodel.HomePockemonViewModelFactory


class PokemonsByTypesFragment : Fragment() {
    private val retrofitService = RetrofitService.getInstance()
    private var _binding: FragmentPokemonsByTypesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomePockemonViewModel
    val args: PokemonsByTypesFragmentArgs by navArgs()
    private val adapterRv = PokemonByTypesAdapter {
        navToDetail(it)
    }
    var listPokemon: MutableList<PokemonUiModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(
            requireActivity(),
            HomePockemonViewModelFactory(PokemonsRepository(retrofitService))
        )[HomePockemonViewModel::class.java]

        viewModel.getPokemonsByTypes(args.type)


        viewModel.pokemonByTypes.observe(requireActivity()) {
            listPokemon.clear()
            for (i in it.pokemon) {
                listPokemon.add(PokemonUiModel().pokemonToPokemonUiModel(i.pokemon))
            }
            adapterRv.notifyDataSetChanged()

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPokemonsByTypesBinding.inflate(inflater, container, false)
        initiRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarCategory)

        (activity as AppCompatActivity?)!!.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            title = args.typeName
            binding.toolbarCategory.setTitleTextColor(resources.getColor(R.color.white))
            binding.toolbarCategory.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }


    }


    override fun onResume() {
        super.onResume()
        initiRecyclerView()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun navToDetail(pokemon: PokemonUiModel) {
        val action = PokemonsByTypesFragmentDirections.actionPokemonsByTypesFragmentToDetailsPokemonFragment(pokemon)
        findNavController().navigate(action)
    }

    private fun initiRecyclerView() {
        binding.rvPokemonTypes.apply {
            adapter = adapterRv
        }
        adapterRv.setDataSet(listPokemon, args.color)
        adapterRv.notifyDataSetChanged()

    }

}