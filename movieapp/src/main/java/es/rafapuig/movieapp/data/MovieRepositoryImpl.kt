package es.rafapuig.movieapp.data

import es.rafapuig.movieapp.data.local.dao.MovieDao
import es.rafapuig.movieapp.data.network.api.MovieService
import es.rafapuig.movieapp.data.network.model.MovieResponse
import es.rafapuig.movieapp.domain.MovieRepository
import es.rafapuig.movieapp.domain.model.Movie

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: MovieDao) : MovieRepository {

    override suspend fun fetchMovies(): List<Movie> {
        val moviesResponse = movieService.getMovies()
        return moviesResponse.results.map { it.toDomain() }
    }

    override fun fetchMoviesFlow(): Flow<List<Movie>> {
        return flow {
            val movieEntities = movieService.getMovies().results.map { it.toDatabase() }
            movieDao.clear()
            movieDao.insertAll(movieEntities)
            val movies = movieDao.getNowPlayingMovies().map { it.toDomain() }
            //emit(movieService.getMovies().results.map { it.toDomain() })
            emit(movies)
        }.flowOn(Dispatchers.IO)
    }
}