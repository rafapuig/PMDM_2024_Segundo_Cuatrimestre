package es.rafapuig.movieapp.ui.ejemplo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EjemploViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(EjemploUiState())
    val uiState = _uiState.asStateFlow()

    fun incrementarContador() {

        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { it.copy(isCalculando = true) }
            withContext(Dispatchers.IO) {
                delay(1000)
            }
            _uiState.update { it.copy(
                contador = it.contador + 1,
                isCalculando = false) }
        }
    }

}