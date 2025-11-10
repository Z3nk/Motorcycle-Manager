package fr.zunkit.motorcyclemanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.zunkit.motorcyclemanager.data.doa.BikeDao
import fr.zunkit.motorcyclemanager.data.doa.CheckDao
import fr.zunkit.motorcyclemanager.data.doa.ConsumableDao
import fr.zunkit.motorcyclemanager.data.doa.HistoryDao
import fr.zunkit.motorcyclemanager.data.models.BikeEntity
import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity
import fr.zunkit.motorcyclemanager.data.models.HistoryEntity

@Database(entities = [BikeEntity::class, ConsumableEntity::class, CheckEntity::class, HistoryEntity::class], version = 3, exportSchema = true)
abstract class MotorCycleDatabase : RoomDatabase() {
    abstract fun bikeDao(): BikeDao

    abstract fun consumableDao(): ConsumableDao

    abstract fun checkDao(): CheckDao

    abstract fun historyDao(): HistoryDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE bikes ADD COLUMN photoUri TEXT")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
            CREATE TABLE histories (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                bikeId INTEGER NOT NULL,
                date TEXT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                FOREIGN KEY (bikeId) REFERENCES bikes(id) ON DELETE CASCADE
            )
        """.trimIndent())

                // Index pour perf
                db.execSQL("CREATE INDEX IF NOT EXISTS index_histories_bikeId ON histories(bikeId)")
            }
        }
    }
}