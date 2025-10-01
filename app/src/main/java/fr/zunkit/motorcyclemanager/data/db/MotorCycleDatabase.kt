package fr.zunkit.motorcyclemanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.zunkit.motorcyclemanager.data.doa.BikeDao
import fr.zunkit.motorcyclemanager.data.doa.CheckDao
import fr.zunkit.motorcyclemanager.data.doa.ConsumableDao
import fr.zunkit.motorcyclemanager.data.models.BikeEntity
import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity

@Database(entities = [BikeEntity::class, ConsumableEntity::class, CheckEntity::class], version = 1, exportSchema = true)
abstract class MotorCycleDatabase : RoomDatabase() {
    abstract fun bikeDao(): BikeDao

    abstract fun consumableDao(): ConsumableDao

    abstract fun checkDao(): CheckDao
}