package com.example.booksroomdemo.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "publishers")
data class Publisher(
    @PrimaryKey(autoGenerate = true) val pId: Int = 0,
    val name: String
)