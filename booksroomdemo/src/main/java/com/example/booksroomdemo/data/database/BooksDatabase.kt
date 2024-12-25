package com.example.booksroomdemo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.booksroomdemo.data.database.entities.Book

@Database(entities = [Book::class], version = 1)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun bookDao() : BookDao
}