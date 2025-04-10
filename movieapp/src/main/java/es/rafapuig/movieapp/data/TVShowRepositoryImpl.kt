package es.rafapuig.movieapp.data

import es.rafapuig.movieapp.data.mappers.toDomain
import es.rafapuig.movieapp.data.network.api.TMDBApiService
import es.rafapuig.movieapp.domain.TvShowRepository
import es.rafapuig.movieapp.domain.model.TVShow
import kotlinx.coroutines.delay
import retrofit2.HttpException
import kotlin.time.Duration.Companion.seconds

class TVShowRepositoryImpl(private val apiService: TMDBApiService) : TvShowRepository {

    override suspend fun fetchTrendingTVShows(): List<TVShow> {
        try {
            delay(2.seconds)
            val response = apiService.fetchTrendingTVShows()
            return response.tvShowList.map { it.toDomain() }
        } catch (ex: HttpException) {
            throw ex
        }

    }

}