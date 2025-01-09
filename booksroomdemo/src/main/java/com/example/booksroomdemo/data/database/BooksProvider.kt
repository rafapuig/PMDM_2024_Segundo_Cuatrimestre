package com.example.booksroomdemo.data.database

import com.example.booksroomdemo.data.database.entities.Book

object BooksProvider {

    val books = listOf(
        Book(
            id = 1,
            title = "Collaborative Software Design: How to facilitate domain modeling decisions",
            cover = "https://m.media-amazon.com/images/I/41uri84VFSL._SL800_.jpg",
            length = 416,
            publisherId = 1
        ),
        Book(
            id = 2,
            title = "Real-World Web Development with .NET 9: Build websites and services using mature and proven ASP.NET Core MVC, Web API, and Umbraco CMS",
            cover = "https://m.media-amazon.com/images/I/41b4qtS5KcL.jpg",
            length = 578,
            publisherId = 2
        )
    )
}