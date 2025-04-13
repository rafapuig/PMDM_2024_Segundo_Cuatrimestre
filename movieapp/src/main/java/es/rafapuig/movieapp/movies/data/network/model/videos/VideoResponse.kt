package es.rafapuig.movieapp.movies.data.network.model.videos


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoResponse(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "iso_3166_1")
    val iso31661: String = "",
    @Json(name = "iso_639_1")
    val iso6391: String = "",
    @Json(name = "key")
    val key: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "official")
    val official: Boolean = false,
    @Json(name = "published_at")
    val publishedAt: String = "",
    @Json(name = "site")
    val site: String = "",
    @Json(name = "size")
    val size: Int = 0,
    @Json(name = "type")
    val type: String = ""
)