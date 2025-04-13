package es.rafapuig.movieapp.movies.data.network.model.videos


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideosResponse(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "TVShowListInfos")
    val results: List<VideoResponse> = listOf()
)