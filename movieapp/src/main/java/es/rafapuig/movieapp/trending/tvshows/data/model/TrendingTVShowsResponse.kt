package es.rafapuig.movieapp.trending.tvshows.data.model


import com.squareup.moshi.Json

data class TrendingTVShowsResponse(
    @Json(name = "page") val page: Int = 0,
    @Json(name = "results") val tvShowList: List<TVShowListInfo> = listOf(),
    @Json(name = "total_pages") val totalPages: Int = 0,
    @Json(name = "total_results") val totalResults: Int = 0
)