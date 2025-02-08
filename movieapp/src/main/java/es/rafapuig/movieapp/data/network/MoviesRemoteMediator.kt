package es.rafapuig.movieapp.data.network

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import es.rafapuig.movieapp.data.local.MoviesDatabase
import es.rafapuig.movieapp.data.local.entity.MovieEntity
import es.rafapuig.movieapp.data.local.entity.RemoteKey
import es.rafapuig.movieapp.data.network.api.MovieService
import es.rafapuig.movieapp.data.toDatabase
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val movieDb: MoviesDatabase,
    private val movieService: MovieService
) : RemoteMediator<Int, MovieEntity>() {

    val TAG = "MOVIES_REMOTE_MEDIATOR"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        return try {

            val firstPageKey = 1

            val loadKey = when (loadType) {

                LoadType.REFRESH -> {
                    Log.d(TAG,"Load type is ${LoadType.REFRESH}, loadKey = $firstPageKey")
                    firstPageKey
                }

                LoadType.PREPEND -> {
                    Log.d(TAG, "Load type is ${LoadType.PREPEND} returning true")
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    Log.d(TAG, "Load type is ${LoadType.APPEND}...")

                    Log.i(TAG,"State:\n Initial load size: ${state.config.initialLoadSize}")
                    Log.i(TAG, "Page size: ${state.config.pageSize}")
                    Log.i(TAG,"Anchor position: ${state.anchorPosition}")
                    Log.i(TAG, "Pages: ${state.pages.size}")

                    Log.d(TAG,"Obtaining remote key for paging...")
                    val remoteKey = movieDb.withTransaction {
                        movieDb.remoteKeyDao.remoteKey()
                    }
                    Log.d(TAG,"Remote key = $remoteKey")

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.nextKey
                }
            }

            Log.d(TAG,"Loading data with loading key = $loadKey")

            val response = movieService.getMovies(page = loadKey)

            val nextPage = response.page + 1
            val nextKey = if(nextPage <= response.totalPages) nextPage else null

            Log.d(TAG, "The next key will be $nextKey")

            movieDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    movieDb.remoteKeyDao.delete()
                    movieDb.movieDao.clearAll()
                }

                // Update RemoteKey for this query.
                movieDb.remoteKeyDao.delete()

                Log.d(TAG,"Inserting a remote key $nextKey")
                movieDb.remoteKeyDao.insertOrReplace(
                    RemoteKey(nextKey)
                )

                val key = movieDb.remoteKeyDao.remoteKey()
                // Cuando insertas null se guarda un 1 (no se por que)
                Log.i(TAG,"The just inserted key = $key")

                val movieEntities = response.results.map { it.toDatabase() }
                movieDb.movieDao.upsertAll(movieEntities)
            }


            // Si la lista resultado esta vacia es que ya no hemos ido a una pagina que no tiene datos
            //MediatorResult.Success(response.results.isEmpty())
            //MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages)
            MediatorResult.Success(endOfPaginationReached = nextKey == null)
        } catch (ex: HttpException) {
            MediatorResult.Error(ex)
        } catch (ex: IOException) {
            MediatorResult.Error(ex)
        }

    }

}