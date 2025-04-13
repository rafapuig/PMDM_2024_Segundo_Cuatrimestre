package es.rafapuig.movieapp.trending.tvshows.domain.model


data class TVShow (
    val id: Int = 0,
    val adult: Boolean = false,
    val backdropPath: String = "",
    val firstAirDate: String = "",
    val genreIds: List<Int> = listOf(),
    val mediaType: String = "",
    val name: String = "",
    val originCountry: List<String> = listOf(),
    val originalLanguage: String = "",
    val originalName: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
)
