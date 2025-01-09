package com.example.booksroomdemo.data.database.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.booksroomdemo.data.database.entities.Book
import com.example.booksroomdemo.data.database.entities.BookTagCrossRef
import com.example.booksroomdemo.data.database.entities.Tag

data class BookWithTags(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "name",
        associateBy = Junction(BookTagCrossRef::class, parentColumn = "bookId", entityColumn = "tag")
    )
    val tags : List<Tag>
)
