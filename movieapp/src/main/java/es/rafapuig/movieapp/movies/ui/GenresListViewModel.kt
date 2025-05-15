package es.rafapuig.movieapp.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.movieapp.TheMovieDBApplication
import es.rafapuig.movieapp.movies.domain.GenreRepository
import es.rafapuig.movieapp.movies.domain.MockGenreRepository
import es.rafapuig.movieapp.movies.domain.model.Genre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GenresListViewModel(val repository: GenreRepository) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = true,
        val genres: List<Genre> = emptyList(),
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    fun getGenres() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                val genres = repository.fetchAllGenres()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        genres = genres
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al cargar los géneros"
                    )
                }
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TheMovieDBApplication)
                GenresListViewModel(application.genreRepository)
            }
        }
    }

}