package es.rafapuig.movieapp.data.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResponse(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "name") val name: String = ""
)