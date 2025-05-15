package es.rafapuig.movieapp.movies.domain

import es.rafapuig.movieapp.movies.domain.model.Genre

class GenreMockRepository : GenreRepository {

    override suspend fun fetchAllGenres(): List<Genre> =
        listOf(
            Genre(id = 1, name = "Drama"),
            Genre(id = 2, name = "Comedy"),
            Genre(id = 3, name = "Action"),
            Genre(id = 4, name = "Thriller"),
            Genre(id = 5, name = "Romance"),
            Genre(id = 6, name = "Adventure"),
            Genre(id = 7, name = "Animation"),
            Genre(id = 8, name = "Fantasy"),
            Genre(id = 9, name = "Horror"),
            Genre(id = 10, name = "Mystery")
        )


}