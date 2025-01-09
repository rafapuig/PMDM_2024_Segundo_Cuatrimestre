package com.example.booksroomdemo.data.database.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "books_tags",
    primaryKeys = ["bookId", "tag"],    //Clave primaria compuesta
    indices = [Index("tag")])
data class BookTagCrossRef(
    val bookId: Long,
    val tag: String
)