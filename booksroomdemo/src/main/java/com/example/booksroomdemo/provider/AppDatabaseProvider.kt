package com.example.booksroomdemo.provider

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.booksroomdemo.data.database.BooksDatabase
import com.example.booksroomdemo.data.database.BooksProvider
import com.example.booksroomdemo.data.database.PublishersProvider
import com.example.booksroomdemo.data.database.TagsProvider
import com.example.booksroomdemo.data.database.entities.BookTagCrossRef

object AppDatabaseProvider : DatabaseProvider<BooksDatabase> {

    override fun getDatabase(context: Context) =
        Room.databaseBuilder(
            context,
            BooksDatabase::class.java, "books.db"
        ).createFromAsset("books_db.db")

            /*.addCallback(object : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                TagsProvider.tags.forEach { tag ->
                    db.insert("tags", CONFLICT_REPLACE,
                        ContentValues().apply { put("name", tag) })
                }

                PublishersProvider.publishers.forEach { publisher ->
                    db.insert("publishers", CONFLICT_REPLACE,
                        ContentValues().apply {
                            put("pId", publisher.pId)
                            put("name", publisher.name)
                        })
                }
            }
        })*/.build()

}