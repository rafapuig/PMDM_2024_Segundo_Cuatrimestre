package es.rafapuig.apiservicesdemo.api

import es.rafapuig.apiservicesdemo.API_TOKEN
import es.rafapuig.apiservicesdemo.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


interface MovieService {
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer $API_TOKEN"
    )
    @GET("movie/now_playing")
    fun getMovies(): Call<MoviesResponse>


    @GET("movie/now_playing")
    fun getMovies2(): Call<MoviesResponse>


    @GET("movie/now_playing")
    suspend fun getMoviesAsyncResponse(): retrofit2.Response<MoviesResponse>


    @GET("movie/now_playing")
    suspend fun getMoviesAsync(): MoviesResponse
}