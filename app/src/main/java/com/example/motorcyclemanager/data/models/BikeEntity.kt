package com.example.motorcyclemanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bikes")
data class BikeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val time: Float
)