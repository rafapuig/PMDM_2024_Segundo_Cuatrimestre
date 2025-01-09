package com.example.booksroomdemo.domain

import com.example.booksroomdemo.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    val books : Flow<List<Book>>

    suspend fun addBook(book: Book)

}