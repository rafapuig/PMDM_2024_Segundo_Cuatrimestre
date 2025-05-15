package es.rafapuig.movieapp.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.PagingData
import es.rafapuig.movieapp.TheMovieDBApplication
import es.rafapuig.movieapp.core.ui.util.getMoviesRepository
import es.rafapuig.movieapp.movies.domain.MovieRepository
import es.rafapuig.movieapp.movies.domain.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NowPlayingMoviesDirectlyFromApiViewModel(
    private val repository: MovieRepository // Dependencia del repositorio de movies
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val moviesList: List<Movie> = emptyList(),
        val error: String? = null
    )


    private var _state = MutableLiveData(UiState())
    val state: LiveData<UiState> = _state

    init {
        fetchMoviesDirectlyFromApiService()
    }


    fun fetchMoviesDirectlyFromApiService() {
        //Fuera de la corrutina se puede usar setValue
        _state.value = state.value?.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val movies = repository.fetchMovies()
                delay(2000)
                //Dentro de la corrutina se debe usar postValue
                _state.postValue(
                    state.value?.copy(
                        isLoading = false,
                        moviesList = movies
                    )
                )
            } catch (ex: Exception) {
                _state.postValue(
                    state.value?.copy(
                        isLoading = false,
                        error = "${ex.message}"
                    )
                )
            }
        }
    }

    //Factoría para crear el ViewModel
    companion object {
        val Factory = viewModelFactory {
            initializer {
                NowPlayingMoviesDirectlyFromApiViewModel(this.getMoviesRepository())
            }
        }
    }
}

