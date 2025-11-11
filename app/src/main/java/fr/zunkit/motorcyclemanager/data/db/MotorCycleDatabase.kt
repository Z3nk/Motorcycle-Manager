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

@Database(
    entities = [BikeEntity::class, ConsumableEntity::class, CheckEntity::class, HistoryEntity::class],
    version = 5,
    exportSchema = true
)
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
                db.execSQL(
                    """
            CREATE TABLE histories (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                bikeId INTEGER NOT NULL,
                date TEXT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                FOREIGN KEY (bikeId) REFERENCES bikes(id) ON DELETE CASCADE
            )
        """.trimIndent()
                )

                db.execSQL("CREATE INDEX IF NOT EXISTS index_histories_bikeId ON histories(bikeId)")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
            CREATE TABLE consumables_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                bikeId INTEGER NOT NULL,
                name TEXT NOT NULL,
                time REAL,               -- nullable = Float?
                currentTime REAL NOT NULL,
                FOREIGN KEY (bikeId) REFERENCES bikes(id) ON DELETE CASCADE
            )
        """.trimIndent())


                db.execSQL("""
            INSERT INTO consumables_new (id, bikeId, name, time, currentTime)
            SELECT id, bikeId, name, time, currentTime
            FROM consumables
        """.trimIndent())

                db.execSQL("DROP TABLE consumables")
                db.execSQL("ALTER TABLE consumables_new RENAME TO consumables")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_consumables_bikeId ON consumables (bikeId)")}
        }
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
            CREATE TABLE histories_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                bikeId INTEGER NOT NULL,
                date TEXT NOT NULL,
                title TEXT NOT NULL,
                time REAL,
                description TEXT NOT NULL,
                FOREIGN KEY (bikeId) REFERENCES bikes(id) ON DELETE CASCADE
            )
        """.trimIndent())


                db.execSQL("""
            INSERT INTO histories_new (id, bikeId, date, title, description)
            SELECT id, bikeId, date, title, description
            FROM histories
        """.trimIndent())

                db.execSQL("DROP TABLE histories")

                db.execSQL("ALTER TABLE histories_new RENAME TO histories")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_histories_bikeId ON histories (bikeId)")
            }
        }
    }
}