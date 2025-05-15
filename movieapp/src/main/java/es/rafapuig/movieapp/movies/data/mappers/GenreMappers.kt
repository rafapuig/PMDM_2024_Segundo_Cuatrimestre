package es.rafapuig.movieapp.movies.data.mappers

import es.rafapuig.movieapp.movies.data.local.entity.GenreEntity
import es.rafapuig.movieapp.movies.data.network.model.GenreResponse
import es.rafapuig.movieapp.movies.domain.model.Genre

fun GenreResponse.toDatabase() = GenreEntity(
    id = id,
    name = name
)

fun GenreEntity.toDomain() = Genre(
    id = id,
    name = name
)