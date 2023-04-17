package com.atejeda.masterdetail.core.mapper

import com.atejeda.masterdetail.data.database.entities.PokemonEntity
import com.atejeda.masterdetail.data.model.Pokemon
import com.atejeda.masterdetail.data.model.Sprites


object ModelToEntityPokemonList : BaseMapper<List<Pokemon>, List<PokemonEntity>> {
    override fun map(type: List<Pokemon>?): List<PokemonEntity> {
        return type?.map {
            PokemonEntity(
                id_api = it.id,
                name = it.name,
                image = it.sprites!!.front_default,
                weight = it.weight,
                height = it.height,
                isFavourite = it.isFavourite,
                types = it.types?.map{ type-> type.type.name }.toString()
            )
        } ?: emptyList()
    }
}

object EntityToModelPokemonList : BaseMapper<List<PokemonEntity>, List<Pokemon>> {
    override fun map(type: List<PokemonEntity>?): List<Pokemon> {
        return type?.map {
            Pokemon(
                id = it.id,
                name = it.name,
                sprites = Sprites(it.image),
                weight = it.weight,
                height = it.height,
                isFavourite = it.isFavourite,
                typesString = it.types

            )
        } ?: emptyList()
    }
}
