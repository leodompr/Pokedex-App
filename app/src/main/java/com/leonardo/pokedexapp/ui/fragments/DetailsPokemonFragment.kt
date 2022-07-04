package com.leonardo.pokedexapp.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
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
import java.io.ByteArrayOutputStream


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

        Glide.with(this) //loading gif pokeballLoading
            .load(R.drawable.pokeball)
            .into(binding.pokeballLoading)

        verifyPokemon() //verify if pokemon get successfuly from api

        binding.selectorFavoritePokemon.setOnClickListener {
            pokemonApi?.let {
                setFavorite()
            }
        }

        binding.sharedPokemonImage.setOnClickListener {
            checkPermissionSharedImage()
        }


    }

    private fun setFavorite() { //set favorite pokemon
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

    private fun sharedImage(title: String) { //share pokemon image
        binding.toolbarDetailsPokemon.isVisible = false
        binding.selectorFavoritePokemon.isVisible = false
        binding.sharedPokemonImage.isVisible = false
        val bitmap = binding.constraintLayoutDetailsPokemon.drawToBitmap()
        val share = Intent(Intent.ACTION_SEND)
        share.type = "image/jpeg"
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                title,
                null
            )
        val imageUri: Uri = Uri.parse(path)
        share.putExtra(Intent.EXTRA_STREAM, imageUri)
        requireActivity().startActivity(Intent.createChooser(share, "Selecione"))
        binding.toolbarDetailsPokemon.isVisible = true
        binding.selectorFavoritePokemon.isVisible = true
        binding.sharedPokemonImage.isVisible = true
    }


    private fun checkPermissionSharedImage() {

        val permissionCheck =
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        } else {
            pokemon?.let {
                sharedImage(it.name)
            }
        }
    }

    private fun initView(pokemon: PokemonUiModel) { //init view
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //set a status bar color
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


    private fun loadingCancel() { //cancel loading
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

    private fun verifyPokemon() { //verify if pokemon get sucessfully
        when (pokemon == null) {
            true -> {
                Handler(Looper.getMainLooper()).postDelayed({  //if not found another call is made
                    getPokemonLoading()
                }, 1000)
            }
            false -> {
                loadingCancel()
            }
        }
    }

    private fun getPokemonLoading() { //get pokemon
        viewModel.getPokemon(args.pokemonName.lowercase())
        loadingCancel()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.pokemon.postValue(null) //clear list pokemon

    }


}