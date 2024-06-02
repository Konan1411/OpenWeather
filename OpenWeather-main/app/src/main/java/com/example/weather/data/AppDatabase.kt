package com.example.weather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, MyCities::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun myCitiesDao(): MyCitiesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "mydatabase.db"
            ).addMigrations(migration1_2).build()

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private val migration1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new User table
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `users` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `username` TEXT NOT NULL,
                        `password` TEXT NOT NULL
                    )
                """)

                // Create the new MyCities table with the foreign key constraint
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `MyCities_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `city` TEXT NOT NULL,
                        `user` INTEGER NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        FOREIGN KEY(`user`) REFERENCES `users`(`id`) ON DELETE CASCADE
                    )
                """)

                // Remove the old MyCities table
                database.execSQL("DROP TABLE `MyCities`")

                // Rename the new MyCities table to the original name
                database.execSQL("ALTER TABLE `MyCities_new` RENAME TO `MyCities`")
            }
        }
    }
}
