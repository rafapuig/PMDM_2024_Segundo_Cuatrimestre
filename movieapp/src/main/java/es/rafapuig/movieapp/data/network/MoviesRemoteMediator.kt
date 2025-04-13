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
import es.rafapuig.movieapp.movies.data.mappers.toDatabase
import es.rafapuig.movieapp.movies.data.network.api.MovieApiService
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val db: MoviesDatabase,
    private val apiService: MovieApiService
) : RemoteMediator<Int, MovieWithGenreDetails>() {


    private val TAG = "MOVIES_REMOTE_MEDIATOR"
    private val FIRST_PAGE_KEY = 1
    private val paginatedServiceId = MovieApiService.PAGINATED_NOW_PLAYING_MOVIES


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
                    val remoteKey = db.withTransaction {
                        db.remoteKeyDao.getByServiceId(paginatedServiceId)
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
                val genresResponse = apiService.getAllMovieGenres()
                db.genreDao.upsertAll(genresResponse.genres.map { it.toDatabase() })
            }

            // Obtener la lista de películas del API Service (la pagina indicada por loadKey)
            val response = apiService.getNowPlayingMovies(page = loadKey)

            // Calcular cual va a ser la siguiente pagina
            val nextPage = response.page + 1
            val nextKey = if (nextPage <= response.totalPages) nextPage else null

            Log.d(TAG, "The next key will be $nextKey")

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeyDao.deleteAll()
                    db.movieDao.clearAll()
                }

                //val movieEntities = response.TVShowListInfos.map { it.toDatabase() }
                //movieDb.movieDao.upsertAll(movieEntities)

                val genres = db.genreDao.getAll()

                response.results.forEach { movieApi ->

                    val movieGenreIds = genres.filter { genre ->
                        genre.id in movieApi.genreIds
                    }.map { genre ->
                        MovieGenreCrossRef(movieId = movieApi.id, genreId = genre.id)
                    }

                    db.movieDao.upsertMovieWithGenreIdsWithTimestamp(movieApi.toDatabase(), movieGenreIds)
                    //movieDb.movieDao.upsertMovieWithGenres(movieApi.toDatabase(), movieGenres)
                }


                // Update RemoteKey for this query.
                db.remoteKeyDao.deleteAll()

                Log.d(TAG, "Inserting a remote key $nextKey")
                db.remoteKeyDao.insertOrReplace(
                    RemoteKey(paginatedServiceId, nextKey)
                )

                val key = db.remoteKeyDao
                    .getByServiceId(paginatedServiceId)
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