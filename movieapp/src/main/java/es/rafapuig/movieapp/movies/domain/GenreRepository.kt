package es.rafapuig.movieapp.movies.domain

import es.rafapuig.movieapp.movies.domain.model.Genre

interface GenreRepository {

    suspend fun fetchAllGenres(): List<Genre>

}