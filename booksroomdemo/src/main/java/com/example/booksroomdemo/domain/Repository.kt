package com.example.booksroomdemo.domain

interface Repository {

    suspend fun getAllBooks()
}