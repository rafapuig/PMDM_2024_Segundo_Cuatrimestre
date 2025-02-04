package es.rafapuig.apiservicesdemo.api

import es.rafapuig.apiservicesdemo.API_TOKEN
import es.rafapuig.apiservicesdemo.model.MoviesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MovieService {
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer $API_TOKEN"
    )
    @GET("movie/now_playing")
    fun getMoviesWithHeaders(): Call<MoviesResponse>


    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): Call<MoviesResponse>


    @GET("movie/now_playing")
    suspend fun getNowPlayingMoviesAsyncResponse(
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): Response<MoviesResponse>


    @GET("movie/now_playing")
    suspend fun getNowPlayingMoviesAsync(
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): MoviesResponse
}