package es.rafapuig.bmi.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asFlow
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.bmi.BmiApplication
import es.rafapuig.bmi.data.BmiState
import es.rafapuig.bmi.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.currentThread


//class BmiViewModel(application: Application) : AndroidViewModel(application) {
class BmiViewModel(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //val repository  = (application as BmiApplication).appContainer.repository

    var weight: Double = 0.0
    var height: Double = 0.0

    //var bmi: Double = 0.0

    val BMI_KEY = "BMI Saved Value"

    private val _bmi = savedStateHandle.getLiveData<Double>(BMI_KEY)//, Double.NaN)
    val bmi: LiveData<Double> = _bmi

    private val _computingBMI = MutableLiveData(false)
    val computingBMI: LiveData<Boolean> = _computingBMI

    private val _bmiState = MutableLiveData<BmiState?>()
    val bmiState: LiveData<BmiState?> = _bmiState

    init {
        viewModelScope.launch {
            bmi.asFlow().collect {
                _bmiState.value = if (it.isFinite()) repository.getQualitativeBMI(it) else null
                //if(it != savedStateHandle[BMI_KEY])
                //savedStateHandle[BMI_KEY] = it
            }
        }
    }


    fun computeBMI() {

        //if(height == 0.0) return

        _computingBMI.value = true

        Log.i("RAFA", "${currentThread().name}: Lanzando corrutina")

        val deferredResult = viewModelScope.async(Dispatchers.IO) {
            Log.i(
                "RAFA",
                "${currentThread().name}: Llamando a computeBMI en el respositorio"
            )

            //funcion suspendida solamente se puede llamar dentro de una corrutina
            val result: Double = repository.computeBMI(weight, height)

            Log.i("RAFA", "${currentThread().name}: Llamada realizada")

            return@async result
        }

        Log.i(
            "RAFA",
            "${currentThread().name}: Haciendo cosas mientras obtengo el resultado"
        )

        Log.i("RAFA", "${currentThread().name}: Lanzando corrutina para esperar el resultado")
        viewModelScope.launch(Dispatchers.IO) {
            // Await es un funcion suspendida, solo se puede llamar dentro de una corrutina
            val result = deferredResult.await()

            withContext(Dispatchers.IO) {
                Log.i("RAFA", "${currentThread().name}: Resultado obtenido")
            }

            withContext(Dispatchers.Main) {
                Log.i("RAFA", "${currentThread().name}: Actualizando BMI")
                _computingBMI.value = false
                _bmi.value = result
            }
        }

        Log.i("RAFA", "${currentThread().name}:Corrutina lanzada con launch")
    }


    /*private fun getResult(): BmiState {
        return repository.getQualitativeBMI(bmi.value!!)
    }*/


    companion object {

        val REPOSITORY_KEY = object : CreationExtras.Key<Repository> {}

        val Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val application = (this[APPLICATION_KEY] as BmiApplication)
                //val repository = application.appContainer.repository
                val repository = this[REPOSITORY_KEY] ?: application.appContainer.repository
                BmiViewModel(repository, savedStateHandle)
            }
        }
    }

}