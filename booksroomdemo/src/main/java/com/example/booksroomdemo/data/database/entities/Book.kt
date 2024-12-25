package com.example.booksroomdemo.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val title:String,
    val length: Int,
    val cover: String? = null,
    val edition: Int = 1,
    val language: String? = null,
    val isbn10: String? = null,
    val isbn13: String? = null
)
