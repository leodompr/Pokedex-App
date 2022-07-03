package com.leonardo.pokedexapp.retrofitservice

import com.leonardo.pokedexapp.model.responsemodel.PokemonDetails
import com.leonardo.pokedexapp.model.responsemodel.PokemonResponse
import com.leonardo.pokedexapp.model.responsemodel.PokemonTypesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("pokemon")
    fun getPokemonResponseList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 40,
    ): retrofit2.Call<PokemonResponse>

    @GET("pokemon/{name}")
    fun getPokemonDetails(@Path("name") name: String): retrofit2.Call<PokemonDetails>

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): retrofit2.Call<PokemonDetails>

    @GET("type/{typeId}")
    fun getPokemonsByType(@Path("typeId") typeId: String): retrofit2.Call<PokemonTypesResponse>


    companion object {
        private val retrofitService: RetrofitService by lazy {  //RetrofitService

            val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)

        }

        fun getInstance(): RetrofitService {
            return retrofitService
        }

    }


}



