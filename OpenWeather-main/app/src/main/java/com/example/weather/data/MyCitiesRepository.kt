/**
 * Manage data operations related to MyCities entities.
 * Provides methods to insert, delete and get all cities.
 */

package com.example.weather.data

class MyCitiesRepository(
    private val dao: MyCitiesDao
) {
    suspend fun insertMyCities(city: MyCities) = dao.insert(city)

    suspend fun deleteMyCities(city: MyCities) = dao.delete(city)

    fun getAllCities() = dao.getAllcities()
}