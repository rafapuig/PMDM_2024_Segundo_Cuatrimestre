package es.rafapuig.movieapp.data.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import es.rafapuig.movieapp.data.local.MoviesDatabase
import es.rafapuig.movieapp.data.local.dao.MovieDao
import es.rafapuig.movieapp.data.local.entity.MovieEntity
import es.rafapuig.movieapp.data.network.api.MovieService
import es.rafapuig.movieapp.data.toDatabase
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val movieDb: MoviesDatabase,
    private val movieService: MovieService
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.let {
                        //(lastItem.id / state.config.pageSize) + 1
                        state.pages.size
                    } ?: 1
                }
            }




            val movies = movieService.getMovies(page = loadKey)

            movieDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    movieDb.movieDao.clearAll()
                }
                val movieEntities = movies.results.map { it.toDatabase() }
                movieDb.movieDao.upsertAll(movieEntities)
            }


            // Si la lista resultado esta vacia es que ya no hemos ido a una pagina que no tiene datos
            MediatorResult.Success(movies.results.isEmpty())
        } catch (ex: HttpException) {
            MediatorResult.Error(ex)
        } catch (ex: IOException) {
            MediatorResult.Error(ex)
        }

    }

}