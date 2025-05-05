package es.rafapuig.movieapp.trending.tvshows.data.api

import es.rafapuig.movieapp.core.data.network.api.ISO
import es.rafapuig.movieapp.trending.tvshows.data.model.TrendingTVShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsApiService {

    object TimeWindow {
        const val DAY = "day"
        const val WEEK = "week"
    }

    enum class Time(val text: String) {
        DAY("day"),
        WEEK("week");

        override fun toString(): String {
            return text;
        }
    }

    @GET("trending/tv/{time_window}")
    suspend fun fetchTrendingTVShows(
        @Path("time_window") timeWindow: String = TimeWindow.DAY,
        @Query("language") language: String = ISO.ES_ES,
        @Query("page") page : Int = 1
    ): TrendingTVShowsResponse
}