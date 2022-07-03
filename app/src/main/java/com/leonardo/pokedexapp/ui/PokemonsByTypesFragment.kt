package com.leonardo.pokedexapp.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
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


        binding.searchEditText.addTextChangedListener(object : TextWatcher {  //Listener do EditText
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString()) //Função que filtra a busca e atualiza a RecycleView

            }
        })


    }


    override fun onResume() {
        super.onResume()
        initiRecyclerView()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun colorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#348BEE")
        }
    }

    private fun navToDetail(pokemon: PokemonUiModel) {
        val action =
            PokemonsByTypesFragmentDirections.actionPokemonsByTypesFragmentToDetailsPokemonFragment(
                pokemon
            )
        findNavController().navigate(action)
    }

    private fun initiRecyclerView() {
        binding.rvPokemonByTypes.apply {
            adapter = adapterRv
        }
        adapterRv.setDataSet(listPokemon, args.color)
        adapterRv.notifyDataSetChanged()

    }


    private fun filter(text: String) {
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