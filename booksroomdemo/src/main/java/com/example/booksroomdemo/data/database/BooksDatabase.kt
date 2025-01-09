package com.example.booksroomdemo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.booksroomdemo.data.database.dao.BookDao
import com.example.booksroomdemo.data.database.dao.PublisherDao
import com.example.booksroomdemo.data.database.entities.Book
import com.example.booksroomdemo.data.database.entities.BookTagCrossRef
import com.example.booksroomdemo.data.database.entities.Publisher
import com.example.booksroomdemo.data.database.entities.Tag


@Database(
    entities = [Book::class, Publisher::class, Tag::class, BookTagCrossRef::class], version = 1)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun bookDao() : BookDao
    abstract fun publisherDao() : PublisherDao
}