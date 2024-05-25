/**
 * Defines the application's Room database.
 * Contains an abstract class named AppDatabase, which inherits from RoomDatabase.
 * Provides a getInstance method for obtaining the database instance of a singleton.
 * Defines the entity classes and DAOs contained in the database.
*/

package com.example.weather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyCities::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun myCitiesDao(): MyCitiesDao

    companion object{
        @Volatile private var instance: AppDatabase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "myCities.db"
            ).build()

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also{
                    instance = it
                }
            }
        }
    }
}