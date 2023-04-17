package com.atejeda.masterdetail.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atejeda.masterdetail.data.database.dao.PokemonDao
import com.atejeda.masterdetail.data.database.entities.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1,exportSchema = false)
abstract class PokemonDatabase :RoomDatabase(){

    abstract fun getPokemonDao(): PokemonDao
}