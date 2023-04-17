package com.atejeda.masterdetail.domain

import com.atejeda.masterdetail.core.datetype.ResultType
import com.atejeda.masterdetail.core.datetype.Result
import com.atejeda.masterdetail.core.mapper.ModelToEntityPokemonList
import com.atejeda.masterdetail.data.PokemonRepository
import com.atejeda.masterdetail.data.model.Pokemon
import javax.inject.Inject

class PokemonUseCase @Inject constructor(private val repository: PokemonRepository) {

    suspend fun getAll(limit: Int,offset:Int): Result<List<Pokemon>>{
        var response = repository.getAll(limit,offset)

        if(response.resultType == ResultType.SUCCESS_LOCAL){
            Result.success(response)
        }
        return if(response.resultType == ResultType.SUCCESS && response.data != null){
            var pokemonsCompletData = response.data!!.map {
                repository.getPokemon(it.url!!)
            }
            repository.insertPokemons(
                ModelToEntityPokemonList.map(
                    pokemonsCompletData
                )
            )
            Result.success(pokemonsCompletData)
        }else{
            repository.getAllFromLocal()
        }
    }

    suspend fun setfavourite(id: Int,checked:Boolean):Boolean = repository.setfavourite(id,checked)


}