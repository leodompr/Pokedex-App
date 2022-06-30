package com.leonardo.pokedexapp.retrofitservice

import com.leonardo.pokedexapp.model.PokemonDetails
import com.leonardo.pokedexapp.model.PokemonResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("pokemon")
     fun getPokemonResponseList(
        @Query("limit") limit: Int = 1154,
    ): retrofit2.Call<PokemonResponse>

    @GET("pokemon/{name}")
     fun getPokemonDetails(@Path("name") name: String): retrofit2.Call<PokemonDetails>


    companion object {
        private val retrofitService : RetrofitService by lazy {  //RetrofitService

            val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)

        }

        fun getInstance() : RetrofitService {
            return retrofitService
        }

    }



}



