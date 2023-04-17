package com.atejeda.masterdetail.data


import com.atejeda.masterdetail.data.network.PokemonService
import com.atejeda.masterdetail.core.mapper.EntityToModelPokemonList
import com.atejeda.masterdetail.data.database.dao.PokemonDao
import com.atejeda.masterdetail.data.database.entities.PokemonEntity
import com.atejeda.masterdetail.data.model.Pokemon
import javax.inject.Inject
import kotlinx.coroutines.coroutineScope
import java.lang.Integer.parseInt
import com.atejeda.masterdetail.core.datetype.Result

class PokemonRepository @Inject constructor(
    private val service: PokemonService,
    private val pokemonDao: PokemonDao
    ) {

    suspend fun getAll(limit: Int,offset:Int): Result<List<Pokemon>>{
        var local = pokemonDao.getAll()
        return if(offset == 0 && local.isNotEmpty()){
            Result.successLocal(EntityToModelPokemonList.map(pokemonDao.getAll()))
        }else{
            service.getAll(limit,offset)
        }
    }

    suspend fun getPokemon(url:String): Pokemon{
        var urlArray = url.split("/")
        return service.getPokemon(parseInt(urlArray[urlArray.size-2])).data!!
    }

    suspend fun getAllFromLocal(): Result<List<Pokemon>> = Result.success(EntityToModelPokemonList.map(pokemonDao.getAll()))

    suspend fun insertPokemons(pokemons:List<PokemonEntity>){
        var pokemonsDao = pokemonDao.getAll()
        coroutineScope {
            pokemons.forEach {
                if (!chekIfExistsPokemon(it,pokemonsDao)) {
                    pokemonDao.insert(it)
                }
            }
        }
    }

    private fun chekIfExistsPokemon(pokemon: PokemonEntity,pokemonList: List<PokemonEntity>): Boolean {
        pokemonList.forEach {
            if (it.id_api == pokemon.id) return true
        }
        return false
    }

    suspend fun setfavourite(id: Int,checked:Boolean):Boolean {
            pokemonDao.updateOne(id,checked)
        return true
    }


}