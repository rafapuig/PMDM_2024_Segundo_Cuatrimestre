package com.example.booksroomdemo.data.database

import com.example.booksroomdemo.data.database.entities.Publisher

object PublishersProvider {

    val publishers = listOf(
        Publisher(1, name = "Manning"),
        Publisher(2, name = "Packt Publishing")
    )

}