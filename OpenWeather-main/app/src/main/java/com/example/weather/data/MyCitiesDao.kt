/**
 * The MyCitiesDao interface is defined, including methods for database operations on MyCities entities.
 * Contains methods for inserting, deleting and querying all cities.
 */

package com.example.weather.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MyCitiesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: MyCities)

    @Delete
    suspend fun delete(city: MyCities)

    @Query("SELECT * FROM MyCities ORDER BY timestamp DESC")
    fun getAllcities(): Flow<List<MyCities>>
}