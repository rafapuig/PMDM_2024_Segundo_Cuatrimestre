package com.example.booksroomdemo.data.database.dto

import androidx.room.Embedded
import com.example.booksroomdemo.data.database.entities.Book
import com.example.booksroomdemo.data.database.entities.Publisher

data class BookWithPublisher(
    @Embedded val book: Book,
    @Embedded val publisher: Publisher
)
