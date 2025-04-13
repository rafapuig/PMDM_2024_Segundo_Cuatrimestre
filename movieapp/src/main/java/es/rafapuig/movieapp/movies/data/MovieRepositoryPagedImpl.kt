package es.rafapuig.movieapp.movies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import es.rafapuig.movieapp.data.local.MoviesDatabase
import es.rafapuig.movieapp.data.network.MoviesRemoteMediator
import es.rafapuig.movieapp.movies.data.network.model.MovieDetailsResponse
import es.rafapuig.movieapp.movies.data.network.model.videos.VideosResponse
import es.rafapuig.movieapp.movies.domain.MovieRepository
import es.rafapuig.movieapp.movies.domain.model.Movie
import es.rafapuig.movieapp.movies.data.mappers.toDomain
import es.rafapuig.movieapp.movies.data.network.api.MovieApiService

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class MovieRepositoryPagedImpl(
    private val apiService: MovieApiService,
    movieDb: MoviesDatabase
) : MovieRepository {

    private val movieDao = movieDb.movieDao

    @OptIn(ExperimentalPagingApi::class)
    val flow = Pager(
        config = PagingConfig(
            pageSize = 5,
            initialLoadSize = 15
        ),
        remoteMediator = MoviesRemoteMediator(movieDb, apiService)
    ) {
        movieDao.getNowPlayingMoviesPaged()
    }.flow.map { it.map { movieEntity -> movieEntity.toDomain() } }



    override suspend fun fetchMovies(): List<Movie> {
        val moviesResponse = apiService.getNowPlayingMovies()
        return moviesResponse.results.map { it.toDomain() }
    }


    override fun fetchMoviesFlow(): Flow<List<Movie>> {
        return emptyFlow()
    }

    override fun fetchMoviesPagingFlow(): Flow<PagingData<Movie>> {
        return flow
    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse {
        return apiService.getMovieDetails(movieId)
    }

    override suspend fun fetchMovieVideos(movieId: Int): VideosResponse {
        return apiService.getVideos(movieId)
    }
}