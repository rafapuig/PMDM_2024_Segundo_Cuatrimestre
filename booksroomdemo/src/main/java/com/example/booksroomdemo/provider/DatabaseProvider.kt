package com.example.booksroomdemo.provider

import android.content.Context
import androidx.room.RoomDatabase

interface DatabaseProvider<T : RoomDatabase> {

    fun getDatabase(context: Context) : T
}