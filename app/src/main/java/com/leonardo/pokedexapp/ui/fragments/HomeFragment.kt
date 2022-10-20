package com.leonardo.pokedexapp.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.databinding.FragmentHomeBinding
import com.leonardo.pokedexapp.model.PokemonUiModel
import com.leonardo.pokedexapp.model.responsemodel.Pokemon
import com.leonardo.pokedexapp.ui.adapters.HomeFragmentListAdapter
import com.leonardo.pokedexapp.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var page: Int = 0
    private val adapterRv = HomeFragmentListAdapter {
        navToDetail(it)
    }
    private val viewModel by viewModel<PokemonViewModel>()

    var listPokemon: MutableList<PokemonUiModel> = mutableListOf()
    var listPokemonApi: MutableList<Pokemon> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val count = listOf(1, 2, 3, 4, 5)
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


        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorStatusBar()


        if (listPokemon.isEmpty()) {
            Glide.with(this)
                .load(R.drawable.pickhu_loading)
                .into(binding.loadingGif)
            loadingInit()

            Handler(Looper.getMainLooper()).postDelayed(this::loadingCancel, 1000)

        }


        binding.btnSortPokemon.setOnClickListener {
            navToDetail(listPokemon.random()) //random pokemon to show in detail

        }

        binding.logoPokedex.setOnClickListener {
            navToFavorites()
        }

        binding.btnFilterList.setOnClickListener {
            openDialogFilter()

        }

        binding.searchEditText.addTextChangedListener(object :
            TextWatcher { //search pokemon by name
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filterListPokemon(p0.toString())

            }
        })


    }


    private fun colorStatusBar() {  //change status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#348BEE")
        }
    }

    private fun navToDetail(pokemon: PokemonUiModel) { //navigate to detail fragment
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsPokemonFragment(pokemon.name)
        findNavController().navigate(action)
    }

    private fun navToFavorites() { //navigate to favorites fragment
        val action = HomeFragmentDirections.actionHomeFragmentToFavoritesFragment()
        findNavController().navigate(action)
    }


    private fun initiRecyclerView() { //initiate recycler view
        adapterRv.setDataSet(listPokemon)
        binding.rvPokemonList.apply {
            adapter = adapterRv
        }
    }

    private fun loadingInit() { //init loading gif
        binding.lvLoading.visibility = View.VISIBLE
        binding.constraintLayoutDetailsPokemon.visibility = View.GONE
    }

    private fun loadingCancel() { //cancel loading gif
        if (listPokemon.isNotEmpty()) {
            binding.lvLoading.visibility = View.GONE
            binding.constraintLayoutDetailsPokemon.visibility = View.VISIBLE
        }
    }

    private fun openDialogFilter() { //open dialog filter pokemon
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_choise_type)


        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_normal)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("1", "#919AA2", "Normal")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_fighting)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("2", "#D6AB85", "Lutador")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_flying)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("3", "#9DD2D2", "Voador")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_poison)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("4", "#B697B7", "Venenoso")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_ground)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("5", "#AE9988", "Terra")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_rock).setOnClickListener {
            dialog.dismiss()
            navToPokemonByTypes("6", "#959595", "Pedra")
        }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_bug).setOnClickListener {
            dialog.dismiss()
            navToPokemonByTypes("7", "#C2CD7D", "Inseto")
        }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_ghost)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("8", "#A5CBA6", "Fantasma")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_steel)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("9", "#C0C0C0", "Aço")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_fire).setOnClickListener {
            dialog.dismiss()
            navToPokemonByTypes("10", "#EB9E64", "Fogo")
        }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_water)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("11", "#7EC1DD", "Água")
            }


        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_grass)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("12", "#97BEAF", "Grama")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_eletric)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("13", "#F1D357", "Elétrico")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_psychic)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("14", "#998BC0", "Psíquico")
            }


        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_ice).setOnClickListener {
            dialog.dismiss()
            navToPokemonByTypes("15", "#BCD0D9", "Gelo")
        }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_dragon)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("16", "#E5989F", "Dragão")
            }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_dark).setOnClickListener {
            dialog.dismiss()
            navToPokemonByTypes("17", "#9597BE", "Sombrio")
        }

        dialog.findViewById<ExtendedFloatingActionButton>(R.id.btn_choise_fairy)
            .setOnClickListener {
                dialog.dismiss()
                navToPokemonByTypes("18", "#D3A7CC", "Fada")
            }


        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }


    private fun navToPokemonByTypes(
        type: String,
        color: String,
        typeName: String
    ) { //navigate to pokemon by type fragment
        val action = HomeFragmentDirections.actionHomeFragmentToPokemonsByTypesFragment(
            type,
            color,
            typeName
        )
        findNavController().navigate(action)
    }

    private fun filterListPokemon(text: String) { //filter list pokemon by name
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