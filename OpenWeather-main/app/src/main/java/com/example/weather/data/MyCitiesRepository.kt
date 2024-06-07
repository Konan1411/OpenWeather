/**
 * Manage data operations related to MyCities entities.
 * Provides methods to insert, delete and get all cities.
 */

package com.example.weather.data

class MyCitiesRepository(
    private val dao: MyCitiesDao
) {
    suspend fun insertMyCity(city: MyCities) = dao.insert(city)

    suspend fun deleteMyCity(city: MyCities) = dao.delete(city)

    fun getCitiesForUser(userId: Int) = dao.getCitiesForUser(userId)

    suspend fun getCityForUser(userId: Int, cityName: String) : MyCities? = dao.getCityForUser(userId,cityName)
}
