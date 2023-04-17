package com.atejeda.masterdetail.di

import android.content.Context
import androidx.room.Room
import com.atejeda.masterdetail.data.database.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val QUOTE_DATABASE_NAME = "pokemon_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PokemonDatabase::class.java, QUOTE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providePokemonDao(db: PokemonDatabase) = db.getPokemonDao()

}