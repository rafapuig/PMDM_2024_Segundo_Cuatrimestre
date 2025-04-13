package es.rafapuig.movieapp.movies.data.network.model

import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "adult") val adult: Boolean = false,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "genre_ids") val genreIds: List<Int> = listOf(),
    @Json(name = "id") val id: Int,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "popularity") val popularity: Double = 0.0,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "title") val title: String = "",
    @Json(name = "video") val video: Boolean = true,
    @Json(name = "vote_average") val voteAverage: Double = 0.0,
    @Json(name = "vote_count") val voteCount: Int = 0
)