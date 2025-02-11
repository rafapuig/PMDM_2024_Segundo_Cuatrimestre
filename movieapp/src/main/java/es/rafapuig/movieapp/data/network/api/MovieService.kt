package es.rafapuig.movieapp.data.network.api

import es.rafapuig.movieapp.data.network.model.GenresResponse
import es.rafapuig.movieapp.data.network.model.MovieDetailsResponse
import es.rafapuig.movieapp.data.network.model.MoviesResponse
import es.rafapuig.movieapp.data.network.model.videos.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    companion object {
        const val PAGINATED_NOW_PLAYING_MOVIES = 1
    }

    @GET("genre/movie/list")
    suspend fun getAllMovieGenres(
        @Query("language") language: String = "es"
    ): GenresResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1,
        @Query("region") region: String = "ES"
    ): MoviesResponse


    @GET("https://api.themoviedb.org/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId : Int,
        @Query("language") language: String = "es-ES"
    ) : MovieDetailsResponse


    @GET("https://api.themoviedb.org/3/movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId : Int,
        @Query("language") language: String = "es-ES"
    ): VideosResponse


}