package com.example.booksroomdemo.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.booksroomdemo.data.database.entities.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( books: List<Book>)

    @Update
    suspend fun update(vararg books: Book)

    @Delete
    suspend fun delete(vararg books: Book)

    @Query("DELETE FROM books")
    suspend fun clear()

    @Query("SELECT * FROM books WHERE title LIKE :search")
    fun find(search: String): List<Book>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun loadBookById(id: Int): Book

    @Query("SELECT * FROM books")
    fun getAllObservable(): Flow<List<Book>>

    fun getBooksDistinctUntilChanged() = getAllObservable().distinctUntilChanged()
}