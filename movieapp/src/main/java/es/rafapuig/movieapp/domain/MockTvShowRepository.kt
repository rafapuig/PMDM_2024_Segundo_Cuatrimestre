package es.rafapuig.movieapp.domain

import es.rafapuig.movieapp.domain.model.TVShow

class MockTvShowRepository : TvShowRepository {

    override suspend fun fetchTrendingTVShows(): List<TVShow> {
        return listOf(
            TVShow(id = 1),
            TVShow(id = 2)
        )
    }
}