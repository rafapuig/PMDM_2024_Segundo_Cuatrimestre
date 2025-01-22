package es.rafapuig.movieapp.data.network.api

import es.rafapuig.movieapp.data.network.model.MoviesResponse
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/now_playing")
    suspend fun getMovies(@Query("language") language: String = "es-ES") : MoviesResponse
}