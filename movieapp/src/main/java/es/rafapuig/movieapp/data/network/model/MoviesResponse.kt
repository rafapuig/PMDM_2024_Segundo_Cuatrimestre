package es.rafapuig.movieapp.data.network.model


import com.squareup.moshi.Json

data class MoviesResponse(
    @Json(name = "dates") val dates: Dates,
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<MovieResponse>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)