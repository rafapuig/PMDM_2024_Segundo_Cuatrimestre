package es.rafapuig.movieapp.data

import es.rafapuig.movieapp.data.local.entity.MovieEntity
import es.rafapuig.movieapp.data.network.model.MovieResponse
import es.rafapuig.movieapp.domain.model.Movie

fun MovieEntity.toDomain() = Movie(
    id,
    title,
    posterPath.orEmpty()
)

fun MovieResponse.toDatabase() = MovieEntity(
    id,
    title,
    posterPath.orEmpty()
)

fun MovieResponse.toDomain() = Movie(
    id,
    title,
    posterPath.orEmpty()
)