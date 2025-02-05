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
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun fetchMovies(): List<Movie> {
        val moviesResponse = movieService.getMovies()
        return moviesResponse.results.map { it.toDomain() }
    }

    override fun fetchMoviesFlow(): Flow<List<Movie>> {
        return flow {
            // Mandamos las peliculas que tenemos en cache primero
            var cachedMovies = movieDao.getNowPlayingMovies().map { it.toDomain() }
            emit(cachedMovies)

            var page: Int = 1
            var totalPages: Int = 0
            //Ahora recuperamos las películas del API
            do {
                val moviesResponse = movieService.getMovies(page = page)
                totalPages = moviesResponse.totalPages
                // Las insertamos en la BD cache
                movieDao.insertAll(moviesResponse.results.map { it.toDatabase() })
            } while (page++ < totalPages)


            val updatedMovies = movieDao.getNowPlayingMovies().map { it.toDomain() }
            //emit(movieService.getMovies().results.map { it.toDomain() })
            // Y volvemos a emitir
            emit(updatedMovies)
        }.flowOn(Dispatchers.IO)
    }
}