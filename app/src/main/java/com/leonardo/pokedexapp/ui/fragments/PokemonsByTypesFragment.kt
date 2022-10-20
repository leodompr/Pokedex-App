package com.leonardo.pokedexapp.ui.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.databinding.FragmentPokemonsByTypesBinding
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.ui.adapters.PokemonByTypesAdapter
import com.leonardo.pokedexapp.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonsByTypesFragment : Fragment() {
//    private val retrofitService = RetrofitService.getInstance()
    private lateinit var binding: FragmentPokemonsByTypesBinding
    private val viewModel by viewModel<PokemonViewModel>()
    private val args: PokemonsByTypesFragmentArgs by navArgs()
    private val adapterRv = PokemonByTypesAdapter {
        navToDetail(it)
    }
    private var listPokemon: MutableList<PokemonUiModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

        binding = FragmentPokemonsByTypesBinding.inflate(inflater, container, false)
        initiRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarPokemonsByTypes)

        (activity as AppCompatActivity?)!!.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = args.typeName
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            binding.toolbarPokemonsByTypes.setTitleTextColor(resources.getColor(R.color.white))
            binding.toolbarPokemonsByTypes.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        colorStatusBar()


        binding.searchEditText.addTextChangedListener(object :
            TextWatcher { //search pokemon by name
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //color status bar
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#348BEE")
        }
    }

    private fun navToDetail(pokemon: PokemonUiModel) { //navigate to detail fragment
        val action =
            PokemonsByTypesFragmentDirections.actionPokemonsByTypesFragmentToDetailsPokemonFragment(
                pokemon.name
            )
        findNavController().navigate(action)
    }

    private fun initiRecyclerView() { //initi recycler view
        binding.rvPokemonByTypes.apply {
            adapter = adapterRv
        }
        adapterRv.setDataSet(listPokemon, args.color)
        adapterRv.notifyDataSetChanged()

    }


    private fun filter(text: String) { //filter pokemon by name
        val listaFiltrada: MutableList<PokemonUiModel> =
            mutableListOf()
        for (s in listPokemon) {
            if (s.name.uppercase().contains(text.uppercase())) {
                listaFiltrada.add(s)
            } else {
                viewModel.getPokemonsDetails(text)
            }
        }
        adapterRv.filterList(listaFiltrada)
    }


}