package com.example.booksroomdemo

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.booksroomdemo.data.database.BooksDatabase
import com.example.booksroomdemo.provider.AppDatabaseProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BooksApplication : Application() {

    //companion object {
    lateinit var database: BooksDatabase
    //}

    override fun onCreate() {
        super.onCreate()

        runBlocking {
            database = AppDatabaseProvider.getDatabase(applicationContext)
        }
    }

}