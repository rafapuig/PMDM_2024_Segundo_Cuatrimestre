package es.rafapuig.movieapp.movies.data.mappers

import es.rafapuig.movieapp.movies.data.local.entity.MovieEntity
import es.rafapuig.movieapp.movies.data.local.entity.MovieWithGenreDetails
import es.rafapuig.movieapp.movies.data.network.model.MovieResponse
import es.rafapuig.movieapp.movies.domain.model.Movie

fun MovieResponse.toDatabase() = MovieEntity(
    id,
    title,
    posterPath.orEmpty(),
    adult = adult,
    backdropPath = backdropPath,
    originalTitle = originalTitle.orEmpty(),
    video = video,
    overview = overview.orEmpty(),
    voteCount = voteCount,
    originalLanguage = originalLanguage.orEmpty(),
    popularity = popularity,
    releaseDate = releaseDate.orEmpty(),
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
    originalTitle = originalTitle.orEmpty(),
    video = video,
    overview = overview.orEmpty(),
    originalLanguage = originalLanguage.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    voteAverage = voteAverage,
    popularity = popularity,
    voteCount = voteCount,
    genreIds = genreIds
)