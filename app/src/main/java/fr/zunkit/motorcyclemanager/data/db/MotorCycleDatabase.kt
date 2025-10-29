package fr.zunkit.motorcyclemanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.zunkit.motorcyclemanager.data.doa.BikeDao
import fr.zunkit.motorcyclemanager.data.doa.CheckDao
import fr.zunkit.motorcyclemanager.data.doa.ConsumableDao
import fr.zunkit.motorcyclemanager.data.models.BikeEntity
import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity

@Database(entities = [BikeEntity::class, ConsumableEntity::class, CheckEntity::class], version = 2, exportSchema = true)
abstract class MotorCycleDatabase : RoomDatabase() {
    abstract fun bikeDao(): BikeDao

    abstract fun consumableDao(): ConsumableDao

    abstract fun checkDao(): CheckDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE bikes ADD COLUMN photoUri TEXT")
            }
        }
    }
}