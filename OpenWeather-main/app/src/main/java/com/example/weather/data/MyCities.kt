package com.example.weather.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "MyCities",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MyCities(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val user: Int,
    val timestamp: Long
)
