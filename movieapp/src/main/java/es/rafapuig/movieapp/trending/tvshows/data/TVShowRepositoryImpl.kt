package es.rafapuig.movieapp.trending.tvshows.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.rafapuig.movieapp.trending.tvshows.data.mappers.toDomain
import es.rafapuig.movieapp.trending.tvshows.domain.TvShowRepository
import es.rafapuig.movieapp.trending.tvshows.domain.model.TVShow
import es.rafapuig.movieapp.trending.tvshows.data.api.TvShowsApiService
import retrofit2.HttpException

class TVShowRepositoryImpl(private val apiService: TvShowsApiService) : TvShowRepository {

    override suspend fun fetchTrendingTVShows(): List<TVShow> {
        try {
            val response = apiService.fetchTrendingTVShows()
            return response.tvShowList.map { it.toDomain() }
        } catch (ex: HttpException) {
            throw ex
        }
    }




}