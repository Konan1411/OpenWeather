/**
 * The MyCities entity class is defined to represent the cities saved by the user.
 * Contains city name and timestamp fields and is labeled as the Room database entity.
 */

package com.example.weather.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyCities(
    @PrimaryKey
    val city : String,

    val timestamp: Long
) : java.io.Serializable