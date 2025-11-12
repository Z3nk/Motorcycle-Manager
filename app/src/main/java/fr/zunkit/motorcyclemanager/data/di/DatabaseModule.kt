package fr.zunkit.motorcyclemanager.data.di

import android.content.Context
import androidx.room.Room
import fr.zunkit.motorcyclemanager.data.db.MotorCycleDatabase
import fr.zunkit.motorcyclemanager.data.doa.BikeDao
import fr.zunkit.motorcyclemanager.data.doa.CheckDao
import fr.zunkit.motorcyclemanager.data.doa.ConsumableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.zunkit.motorcyclemanager.data.doa.HistoryDao
import fr.zunkit.motorcyclemanager.data.doa.InvoiceDao
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
        )
            .addMigrations(MotorCycleDatabase.MIGRATION_1_2)
            .addMigrations(MotorCycleDatabase.MIGRATION_2_3)
            .addMigrations(MotorCycleDatabase.MIGRATION_3_4)
            .addMigrations(MotorCycleDatabase.MIGRATION_4_5)
            .addMigrations(MotorCycleDatabase.MIGRATION_5_6)
            .build()
    }

    @Provides
    @Singleton
    fun provideBikeDao(database: MotorCycleDatabase): BikeDao {
        return database.bikeDao()
    }

    @Provides
    @Singleton
    fun provideConsumableDao(database: MotorCycleDatabase): ConsumableDao {
        return database.consumableDao()
    }

    @Provides
    @Singleton
    fun provideCheckDao(database: MotorCycleDatabase): CheckDao {
        return database.checkDao()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(database: MotorCycleDatabase): HistoryDao {
        return database.historyDao()
    }
    @Provides
    @Singleton
    fun provideInvoiceDao(database: MotorCycleDatabase): InvoiceDao {
        return database.invoiceDao()
    }
}