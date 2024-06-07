package com.example.weather.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "MyCities",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["city", "user"], unique = true)]
)
data class MyCities(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val city: String,
    val user: Int,
    val timestamp: Long
)
