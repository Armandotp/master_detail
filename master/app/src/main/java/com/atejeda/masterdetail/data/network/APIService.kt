package com.atejeda.masterdetail.data.network

import com.atejeda.masterdetail.data.model.Pokemon
import com.atejeda.masterdetail.data.model.PokemonAll
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET("pokemon/")
    suspend fun getAll(@Query("limit") limit: Int,@Query("offset") offset: Int): Response<PokemonAll>

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Response<Pokemon>

}