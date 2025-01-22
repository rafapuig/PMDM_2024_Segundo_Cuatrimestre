package es.rafapuig.apiservicesdemo.model

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>
)