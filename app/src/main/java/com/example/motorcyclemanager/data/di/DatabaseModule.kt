package com.example.motorcyclemanager.data.di

import android.content.Context
import androidx.room.Room
import com.example.motorcyclemanager.data.db.MotorCycleDatabase
import com.example.motorcyclemanager.data.doa.BikeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MotorCycleDatabase {
        return Room.databaseBuilder(
            context,
            MotorCycleDatabase::class.java,
            "app_database"
        ).build() // Add migrations if needed later
    }

    @Provides
    @Singleton
    fun provideBikeDao(database: MotorCycleDatabase): BikeDao {
        return database.bikeDao()
    }
}