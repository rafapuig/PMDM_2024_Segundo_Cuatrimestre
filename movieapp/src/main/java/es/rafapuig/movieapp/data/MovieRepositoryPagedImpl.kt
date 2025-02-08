package es.rafapuig.movieapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import es.rafapuig.movieapp.data.local.MoviesDatabase
import es.rafapuig.movieapp.data.network.MoviesRemoteMediator
import es.rafapuig.movieapp.data.network.api.MovieService
import es.rafapuig.movieapp.domain.MovieRepository
import es.rafapuig.movieapp.domain.model.Movie

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MovieRepositoryPagedImpl(
    private val movieService: MovieService,
    movieDb: MoviesDatabase
) : MovieRepository {

    private val movieDao = movieDb.movieDao

    @OptIn(ExperimentalPagingApi::class)
    val flow = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20),
        remoteMediator = MoviesRemoteMediator(movieDb, movieService)
    ) {
        movieDao.getNowPlayingMoviesPaged()
    }.flow.map { it.map { movieEntity -> movieEntity.toDomain() } }

    override suspend fun fetchMovies(): List<Movie> {
        val moviesResponse = movieService.getMovies()
        return moviesResponse.results.map { it.toDomain() }
    }

    override fun fetchMoviesFlow(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun fetchMoviesPagingFlow(): Flow<PagingData<Movie>> {
        return flow
    }
}