package es.rafapuig.movieapp.movies.data.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCountryResponse(
    @Json(name = "iso_3166_1")
    val iso31661: String = "",
    @Json(name = "name")
    val name: String = ""
)