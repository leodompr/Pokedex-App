package com.leonardo.pokedexapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.databinding.FragmentHomeBinding
import com.leonardo.pokedexapp.model.Pokemon
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
    private var page: Int = 0
    private val adapterRv = HomeFragmentListAdapter {
        navToDetail(it)
    }
    private lateinit var viewModel: HomePockemonViewModel
    var listPokemon: MutableList<PokemonUiModel> = mutableListOf()
    var listPokemonApi: MutableList<Pokemon> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            HomePockemonViewModelFactory(PokemonsRepository(retrofitService))
        )[HomePockemonViewModel::class.java]

        val count = listOf(1, 2, 3)
        for (i in count) {
            viewModel.getPokemons(page)
            page++
        }

        viewModel.pokemonList.observe(requireActivity()) {
            for (pokemon in it.results) {
                if (listPokemonApi.contains(pokemon)) {
                    continue
                } else {
                    viewModel.getPokemonsDetails(pokemon.name)
                }
            }
        }


            viewModel.pokemonDetails.observe(requireActivity()) {
                try {
                    if (listPokemon.contains(PokemonUiModel().pokemonDetaisToPokemonUiModel(it))) {
                        return@observe
                    } else {
                        listPokemon.add(PokemonUiModel().pokemonDetaisToPokemonUiModel(it))
                        adapterRv.notifyDataSetChanged()

                    }
                    adapterRv.setDataSet(listPokemon)
                    adapterRv.notifyDataSetChanged()
                } catch (e: Exception) {
                }
            }



    }

    override fun onResume() {
        super.onResume()

        viewModel.getPokemons(page)
        initiRecyclerView()


        binding.rvPokemonList.addOnScrollListener(object :
            androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = binding.rvPokemonList.childCount
                    val totalItemCount = binding.rvPokemonList.layoutManager?.itemCount ?: 0
                    val pastVisibleItems =
                        (binding.rvPokemonList.layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        page++
                        viewModel.getPokemons(page)
                    }
                }
            }
        })

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

        binding.btnSortPokemon.setOnClickListener {
            navToDetail(listPokemon.random())

        }


        binding.searchEditText.addTextChangedListener(object : TextWatcher {  //Listener do EditText
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString()) //Função que filtra a busca e atualiza a RecycleView

            }
        })

//
//        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
//            if(actionId == EditorInfo.IME_ACTION_DONE){
//                binding.progressBar.visibility = View.VISIBLE
//                true
//            } else {
//                false
//            }
//        }


    }

    private fun navToDetail(pokemon: PokemonUiModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsPokemonFragment(pokemon)
        findNavController().navigate(action)
    }




    private fun initiRecyclerView() {
        adapterRv.setDataSet(listPokemon)
        binding.rvPokemonList.apply {
            adapter = adapterRv
        }
    }


    private fun filter(text: String) { // FAZ O FILTRO DO SERARCH VIEW
        val listaFiltrada: MutableList<PokemonUiModel> =
            mutableListOf()   //nova lista que conterá os dados filtrados
        for (s in listPokemon) {
            if (s.name.uppercase().contains(text.uppercase())) {
                listaFiltrada.add(s)
            } else {
                viewModel.getPokemonsDetails(text)
            }
        }
        adapterRv.filterList(listaFiltrada) //Metodo do adapter que atualiza a lista
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}