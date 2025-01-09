package com.example.booksroomdemo.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.booksroomdemo.data.database.dto.BookAndPublisher
import com.example.booksroomdemo.data.database.dto.BookWithPublisher
import com.example.booksroomdemo.data.database.dto.BookWithTags
import com.example.booksroomdemo.data.database.entities.Book
import com.example.booksroomdemo.data.database.entities.BookTagCrossRef
import com.example.booksroomdemo.data.database.entities.Tag
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


    @Query("SELECT * FROM books INNER JOIN publishers ON books.publisherId = publishers.pId")
    fun getBooksWithPublisher(): Flow<List<BookWithPublisher>>

    @Transaction
    @Query("SELECT * FROM books")
    fun getBooksAndPublisher(): Flow<List<BookAndPublisher>>

    @Transaction
    @Query("SELECT * FROM books")
    fun getBooksWithTags() : Flow<List<BookWithTags>>

    @Insert
    suspend fun addTag(bookTag : BookTagCrossRef)
}