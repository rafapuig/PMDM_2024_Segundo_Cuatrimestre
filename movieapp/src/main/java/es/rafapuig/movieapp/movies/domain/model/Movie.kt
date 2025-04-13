package es.rafapuig.movieapp.movies.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String = "",
    val adult: Boolean = false,
    val backdropPath: String? = null,
    var genreIds: List<Int> = emptyList(),
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val releaseDate: String = "",
    val video: Boolean = true,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
)
