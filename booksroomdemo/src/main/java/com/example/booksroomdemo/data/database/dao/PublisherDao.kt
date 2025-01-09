package com.example.booksroomdemo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksroomdemo.data.database.entities.Book
import com.example.booksroomdemo.data.database.entities.Publisher

@Dao
interface PublisherDao {

    @Insert
    suspend fun insert(vararg publisher: Publisher)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(publisher: List<Publisher>)

    @Query("SELECT * FROM publishers")
    fun getAll() : List<Publisher>

    @Query("SELECT * FROM publishers INNER JOIN books ON books.publisherId = publishers.pId")
    fun getPublishersWithBooksMap() : Map<Publisher, List<Book>>

}