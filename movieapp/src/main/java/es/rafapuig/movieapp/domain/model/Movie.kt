package es.rafapuig.movieapp.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val adult: Boolean,
    val backdropPath: String?,
    var genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
