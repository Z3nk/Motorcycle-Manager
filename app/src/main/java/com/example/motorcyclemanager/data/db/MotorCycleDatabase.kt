package com.example.motorcyclemanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.motorcyclemanager.data.doa.BikeDao
import com.example.motorcyclemanager.data.models.BikeEntity
import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.models.ConsumableEntity

@Database(entities = [BikeEntity::class, ConsumableEntity::class, CheckEntity::class], version = 1, exportSchema = false)
abstract class MotorCycleDatabase : RoomDatabase() {
    abstract fun bikeDao(): BikeDao
}