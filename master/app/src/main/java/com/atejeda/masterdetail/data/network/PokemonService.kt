package com.atejeda.masterdetail.data.network

import com.atejeda.masterdetail.core.datetype.Result
import com.atejeda.masterdetail.core.exceptions.handleNetworkExceptions
import com.atejeda.masterdetail.data.model.Pokemon
import com.atejeda.masterdetail.di.NetworkModule
import java.lang.Exception
import javax.inject.Inject

class PokemonService @Inject constructor(){

    suspend fun getAll(limit:Int,offset:Int): Result<List<Pokemon>> {
        return try {
            var call = NetworkModule.provideApiService(
                    NetworkModule.provideRetrofit()).getAll(limit,offset)
            if (call.isSuccessful) {
                return Result.success(call.body()?.results)
            } else {
                return Result.error("server${call.code()}")
            }
        }catch (e: Exception){
            e.printStackTrace()
            Result.error(handleNetworkExceptions(e))
        }
    }

    suspend fun getPokemon(id:Int): Result<Pokemon> {
        return try {
            var call = NetworkModule.provideApiService(
                NetworkModule.provideRetrofit()).getPokemon(id)
            if (call.isSuccessful) {
                var response = call.body()
                response?.typesString = response?.types?.map{ type-> type.type.name }.toString()
                return Result.success(response)
            } else {
                return Result.error("server${call.code()}")
            }
        }catch (e: Exception){
            e.printStackTrace()
            Result.error(handleNetworkExceptions(e))
        }
    }


}