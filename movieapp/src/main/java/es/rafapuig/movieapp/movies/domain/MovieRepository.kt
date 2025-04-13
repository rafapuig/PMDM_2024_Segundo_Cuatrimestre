package es.rafapuig.movieapp.movies.domain

import androidx.paging.PagingData
import es.rafapuig.movieapp.movies.data.network.model.MovieDetailsResponse
import es.rafapuig.movieapp.movies.data.network.model.videos.VideosResponse
import es.rafapuig.movieapp.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun fetchMovies(): List<Movie>

    fun fetchMoviesFlow(): Flow<List<Movie>>

    fun fetchMoviesPagingFlow(): Flow<PagingData<Movie>>

    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse? = null

    suspend fun fetchMovieVideos(movieId: Int): VideosResponse? = null

}