package es.rafapuig.movieapp.data.mappers

import es.rafapuig.movieapp.data.local.entity.MovieEntity
import es.rafapuig.movieapp.data.local.entity.MovieWithGenreDetails
import es.rafapuig.movieapp.data.network.model.MovieResponse
import es.rafapuig.movieapp.domain.model.Movie

fun MovieResponse.toDatabase() = MovieEntity(
    id,
    title,
    posterPath.orEmpty(),
    adult = adult,
    backdropPath = backdropPath,
    originalTitle = originalTitle,
    video = video,
    overview = overview,
    voteCount = voteCount,
    originalLanguage = originalLanguage,
    popularity = popularity,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun MovieEntity.toDomain() = Movie(
    id,
    title,
    posterPath.orEmpty(),
    adult = adult,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    popularity = popularity,
    overview = overview,
    video = video,
    voteCount = voteCount,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    genreIds = emptyList()
)

fun MovieWithGenreDetails.toDomain() =
    movie.toDomain().also { movie -> movie.genreIds = genres.map { it.id } }


fun MovieResponse.toDomain() = Movie(
    id,
    title,
    posterPath.orEmpty(),
    adult = adult,
    backdropPath = backdropPath,
    originalTitle = originalTitle,
    video = video,
    overview = overview,
    originalLanguage = originalLanguage,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    popularity = popularity,
    voteCount = voteCount,
    genreIds = genreIds
)