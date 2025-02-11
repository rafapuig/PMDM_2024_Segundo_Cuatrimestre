package es.rafapuig.movieapp.domain

import androidx.paging.PagingData
import es.rafapuig.movieapp.data.network.model.MovieDetailsResponse
import es.rafapuig.movieapp.data.network.model.MovieResponse
import es.rafapuig.movieapp.data.network.model.videos.VideosResponse
import es.rafapuig.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun fetchMovies(): List<Movie>

    fun fetchMoviesFlow(): Flow<List<Movie>>

    fun fetchMoviesPagingFlow(): Flow<PagingData<Movie>>

    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse? = null

    suspend fun fetchMovieVideos(movieId: Int): VideosResponse? = null

}