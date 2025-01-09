package com.example.booksroomdemo.data.database.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.example.booksroomdemo.data.database.entities.Book
import com.example.booksroomdemo.data.database.entities.Publisher

data class BookAndPublisher (
    @Embedded val book : Book,
    @Relation(
        parentColumn = "publisherId",
        entityColumn = "pId"
    )
    val publisher:Publisher
)