package es.rafapuig.movieapp.trending.tvshows.data.model


import com.squareup.moshi.Json
import es.rafapuig.movieapp.trending.tvshows.domain.model.TVShow

data class TVShowListInfo(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "adult") val adult: Boolean = false,
    @Json(name = "backdrop_path") val backdropPath: String = "",
    @Json(name = "first_air_date") val firstAirDate: String = "",
    @Json(name = "genre_ids") val genreIds: List<Int> = listOf(),
    @Json(name = "media_type")  val mediaType: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "origin_country") val originCountry: List<String> = listOf(),
    @Json(name = "original_language") val originalLanguage: String = "",
    @Json(name = "original_name") val originalName: String = "",
    @Json(name = "overview") val overview: String = "",
    @Json(name = "popularity") val popularity: Double = 0.0,
    @Json(name = "poster_path") val posterPath: String = "",
    @Json(name = "vote_average") val voteAverage: Double = 0.0,
    @Json(name = "vote_count") val voteCount: Int = 0
)