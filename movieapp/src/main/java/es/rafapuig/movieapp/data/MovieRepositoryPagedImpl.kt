package es.rafapuig.movieapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import es.rafapuig.movieapp.data.local.MoviesDatabase
import es.rafapuig.movieapp.data.mappers.toDomain
import es.rafapuig.movieapp.data.network.MoviesRemoteMediator
import es.rafapuig.movieapp.data.network.api.TMDBApiService
import es.rafapuig.movieapp.data.network.model.MovieDetailsResponse
import es.rafapuig.movieapp.data.network.model.videos.VideosResponse
import es.rafapuig.movieapp.domain.MovieRepository
import es.rafapuig.movieapp.domain.model.Movie

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryPagedImpl(
    private val TMDBApiService: TMDBApiService,
    movieDb: MoviesDatabase
) : MovieRepository {

    private val movieDao = movieDb.movieDao

    @OptIn(ExperimentalPagingApi::class)
    val flow = Pager(
        config = PagingConfig(
            pageSize = 5,
            initialLoadSize = 15
        ),
        remoteMediator = MoviesRemoteMediator(movieDb, TMDBApiService)
    ) {
        movieDao.getNowPlayingMoviesPaged()
    }.flow.map { it.map { movieEntity -> movieEntity.toDomain() } }



    override suspend fun fetchMovies(): List<Movie> {
        val moviesResponse = TMDBApiService.getNowPlayingMovies()
        return moviesResponse.results.map { it.toDomain() }
    }


    override fun fetchMoviesFlow(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun fetchMoviesPagingFlow(): Flow<PagingData<Movie>> {
        return flow
    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse {
        return TMDBApiService.getMovieDetails(movieId)
    }

    override suspend fun fetchMovieVideos(movieId: Int): VideosResponse {
        return TMDBApiService.getVideos(movieId)
    }
}