package es.rafapuig.movieapp.data

import androidx.paging.PagingData
import es.rafapuig.movieapp.data.local.dao.MovieDao
import es.rafapuig.movieapp.data.mappers.toDatabase
import es.rafapuig.movieapp.data.mappers.toDomain
import es.rafapuig.movieapp.data.network.api.TMDBApiService
import es.rafapuig.movieapp.domain.MovieRepository
import es.rafapuig.movieapp.domain.model.Movie

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(
    private val TMDBApiService: TMDBApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun fetchMovies(): List<Movie> {
        val moviesResponse = TMDBApiService.getNowPlayingMovies()
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
                val moviesResponse = TMDBApiService.getNowPlayingMovies(page = page)
                totalPages = moviesResponse.totalPages
                // Las insertamos en la BD cache
                movieDao.insertAll(moviesResponse.results.map { it.toDatabase() })
            } while (page++ < totalPages)


            val updatedMovies = movieDao.getNowPlayingMovies().map { it.toDomain() }
            //emit(movieService.getMovies().TVShowListInfos.map { it.toDomain() })
            // Y volvemos a emitir
            emit(updatedMovies)
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchMoviesPagingFlow(): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }
}