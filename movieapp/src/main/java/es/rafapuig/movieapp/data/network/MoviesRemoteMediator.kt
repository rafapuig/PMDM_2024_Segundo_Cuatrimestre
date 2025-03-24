package es.rafapuig.movieapp.data.network

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import es.rafapuig.movieapp.data.local.MoviesDatabase
import es.rafapuig.movieapp.data.local.entity.MovieGenreCrossRef
import es.rafapuig.movieapp.data.local.entity.MovieWithGenreDetails
import es.rafapuig.movieapp.data.local.entity.RemoteKey
import es.rafapuig.movieapp.data.mappers.toDatabase
import es.rafapuig.movieapp.data.network.api.TMDBApiService
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val movieDb: MoviesDatabase,
    private val TMDBApiService: TMDBApiService
) : RemoteMediator<Int, MovieWithGenreDetails>() {


    private val TAG = "MOVIES_REMOTE_MEDIATOR"
    private val FIRST_PAGE_KEY = 1


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieWithGenreDetails>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {

                LoadType.REFRESH -> {
                    Log.d(TAG, "Load type is ${LoadType.REFRESH}, loadKey = $FIRST_PAGE_KEY")
                    FIRST_PAGE_KEY
                }

                LoadType.PREPEND -> {
                    Log.d(TAG, "Load type is ${LoadType.PREPEND} returning true")
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    Log.d(TAG, "Load type is ${LoadType.APPEND}...")

                    Log.i(TAG, "State:\n Initial load size: ${state.config.initialLoadSize}")
                    Log.i(TAG, "Page size: ${state.config.pageSize}")
                    Log.i(TAG, "Anchor position: ${state.anchorPosition}")
                    Log.i(TAG, "Pages: ${state.pages.size}")

                    Log.d(TAG, "Obtaining remote key for paging...")
                    val remoteKey = movieDb.withTransaction {
                        movieDb.remoteKeyDao.getByServiceId(TMDBApiService.PAGINATED_NOW_PLAYING_MOVIES)
                    }
                    Log.d(TAG, "Remote key = $remoteKey")

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }

            Log.d(TAG, "Loading data with loading key = $loadKey")

            // Insertar los géneros en la BD
            if (loadKey == FIRST_PAGE_KEY) {
                val genresResponse = TMDBApiService.getAllMovieGenres()
                movieDb.genreDao.upsertAll(genresResponse.genres.map { it.toDatabase() })
            }

            // Obtener la lista de películas del API Service (la pagina indicada por loadKey)
            val response = TMDBApiService.getNowPlayingMovies(page = loadKey)

            // Calcular cual va a ser la siguiente pagina
            val nextPage = response.page + 1
            val nextKey = if (nextPage <= response.totalPages) nextPage else null

            Log.d(TAG, "The next key will be $nextKey")

            movieDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDb.remoteKeyDao.deleteAll()
                    movieDb.movieDao.clearAll()
                }

                //val movieEntities = response.TVShowListInfos.map { it.toDatabase() }
                //movieDb.movieDao.upsertAll(movieEntities)

                val genres = movieDb.genreDao.getAll()

                response.results.forEach { movieApi ->

                    val movieGenreIds = genres.filter { genre ->
                        genre.id in movieApi.genreIds
                    }.map { genre ->
                        MovieGenreCrossRef(movieId = movieApi.id, genreId = genre.id)
                    }

                    movieDb.movieDao.upsertMovieWithGenreIdsWithTimestamp(movieApi.toDatabase(), movieGenreIds)
                    //movieDb.movieDao.upsertMovieWithGenres(movieApi.toDatabase(), movieGenres)
                }


                // Update RemoteKey for this query.
                movieDb.remoteKeyDao.deleteAll()

                Log.d(TAG, "Inserting a remote key $nextKey")
                movieDb.remoteKeyDao.insertOrReplace(
                    RemoteKey(TMDBApiService.PAGINATED_NOW_PLAYING_MOVIES, nextKey)
                )

                val key = movieDb.remoteKeyDao
                    .getByServiceId(TMDBApiService.PAGINATED_NOW_PLAYING_MOVIES)
                // Cuando insertas null se guarda un 1 (no se por que)
                Log.i(TAG, "The just inserted key = $key")
            }


            // Si la lista resultado esta vacia es que ya no hemos ido a una pagina que no tiene datos
            //MediatorResult.Success(response.TVShowListInfos.isEmpty())
            //MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages)
            MediatorResult.Success(endOfPaginationReached = nextKey == null)
        } catch (ex: HttpException) {
            MediatorResult.Error(ex)
        } catch (ex: IOException) {
            MediatorResult.Error(ex)
        }

    }

}