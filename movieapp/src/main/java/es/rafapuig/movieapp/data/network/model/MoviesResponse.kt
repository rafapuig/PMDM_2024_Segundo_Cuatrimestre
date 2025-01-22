package es.rafapuig.movieapp.data.network.model

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>
)