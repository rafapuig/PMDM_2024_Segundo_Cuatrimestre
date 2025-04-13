package es.rafapuig.movieapp.movies.data.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenresResponse(
    @Json(name = "genres")
    val genres: List<GenreResponse> = listOf()
)