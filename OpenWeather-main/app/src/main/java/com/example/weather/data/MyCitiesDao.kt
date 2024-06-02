/**
 * The MyCitiesDao interface is defined, including methods for database operations on MyCities entities.
 * Contains methods for inserting, deleting and querying all cities.
 */

package com.example.weather.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MyCitiesDao {
    @Insert
    suspend fun insert(city: MyCities)

    @Delete
    suspend fun delete(city: MyCities)

    @Query("SELECT * FROM MyCities WHERE user = :userId")
    fun getCitiesForUser(userId: Int): Flow<List<MyCities>>
}
