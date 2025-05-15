package es.rafapuig.movieapp.movies.data

import es.rafapuig.movieapp.movies.data.local.dao.GenreDao
import es.rafapuig.movieapp.movies.data.mappers.toDomain
import es.rafapuig.movieapp.movies.domain.GenreRepository
import es.rafapuig.movieapp.movies.domain.model.Genre

class GenreRepositoryImpl(val genreDao: GenreDao) : GenreRepository {

    override suspend fun fetchAllGenres(): List<Genre> =
        genreDao.getAll().map { it.toDomain() }

}