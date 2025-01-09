package com.example.booksroomdemo.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.booksroomdemo.domain.model.Book as BookModel

@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = Publisher::class,
            parentColumns = ["pId"],
            childColumns = ["publisherId"],
            onDelete = ForeignKey.SET_NULL
        )
    ], indices = [Index("publisherId")]
)
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val length: Int,
    val cover: String? = null,
    val edition: Int = 1,
    val language: String? = null,
    val isbn10: String? = null,
    val isbn13: String? = null,
    val publisherId: Int? = null
)


fun Book.toDomain(): BookModel =
    BookModel(this.title, cover.orEmpty(), null)

