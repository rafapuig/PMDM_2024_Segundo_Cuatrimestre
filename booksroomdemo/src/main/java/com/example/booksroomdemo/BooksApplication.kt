package com.example.booksroomdemo

import android.app.Application
import androidx.room.Room
import com.example.booksroomdemo.data.database.BooksDatabase

class BooksApplication : Application() {

    lateinit var database : BooksDatabase

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            BooksDatabase::class.java, "books.db"
        ).build()

    }
}