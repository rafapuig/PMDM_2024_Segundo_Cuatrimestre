package es.rafapuig.movieapp.data.mappers

import es.rafapuig.movieapp.data.network.model.TVShowListInfo
import es.rafapuig.movieapp.domain.model.TVShow

fun TVShowListInfo.toDomain() = TVShow(
    id = id,
    name = name,
    overview = overview,
    posterPath = posterPath
)