package com.atejeda.masterdetail.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atejeda.masterdetail.data.database.entities.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    suspend fun getAll():List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons:List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAll()

    @Query("UPDATE pokemon set isFavourite = :checked WHERE id_api = :id")
    suspend fun updateOne(id: Int,checked:Boolean)
}