package es.rafapuig.movieapp.domain

import es.rafapuig.movieapp.domain.model.TVShow

interface TvShowRepository {

    suspend fun fetchTrendingTVShows() : List<TVShow>

}