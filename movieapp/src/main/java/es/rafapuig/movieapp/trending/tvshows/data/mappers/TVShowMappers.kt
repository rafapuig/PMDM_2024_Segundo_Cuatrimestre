package es.rafapuig.movieapp.trending.tvshows.data.mappers

import es.rafapuig.movieapp.trending.tvshows.data.model.TVShowListInfo
import es.rafapuig.movieapp.trending.tvshows.domain.model.TVShow

fun TVShowListInfo.toDomain() = TVShow(
    id = id,
    name = name,
    overview = overview,
    posterPath = posterPath
)