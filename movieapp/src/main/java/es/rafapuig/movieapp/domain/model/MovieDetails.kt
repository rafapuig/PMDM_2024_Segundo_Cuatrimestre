package es.rafapuig.movieapp.domain.model

import es.rafapuig.movieapp.data.network.model.GenreResponse
import es.rafapuig.movieapp.data.network.model.ProductionCompanyResponse
import es.rafapuig.movieapp.data.network.model.ProductionCountryResponse
import es.rafapuig.movieapp.data.network.model.SpokenLanguageResponse

data class MovieDetails(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val belongsToCollection: Any? = null,
    val budget: Int = 0,
    val genres: List<GenreResponse> = listOf(),
    val homepage: String = "",
    val id: Int = 0,
    val imdbId: String = "",
    val originCountry: List<String> = listOf(),
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val productionCompanies: List<ProductionCompanyResponse> = listOf(),
    val productionCountries: List<ProductionCountryResponse> = listOf(),
    val releaseDate: String = "",
    val revenue: Int = 0,
    val runtime: Int = 0,
    val spokenLanguages: List<SpokenLanguageResponse> = listOf(),
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
)
