package es.rafapuig.bmi.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.bmi.BmiApplication
import es.rafapuig.bmi.data.BmiState
import es.rafapuig.bmi.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//class BmiViewModel(application: Application) : AndroidViewModel(application) {
class BmiViewModel(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

    //val repository  = (application as BmiApplication).appContainer.repository

    var weight: Double = 0.0
    var height: Double = 0.0

    //var bmi: Double = 0.0

    val BMI_KEY = "BMI Saved Value"

    private val _bmi = savedStateHandle.getLiveData<Double>(BMI_KEY)
    val bmi: LiveData<Double> = _bmi

    private val _computingBMI = MutableLiveData<Boolean>(false)
    val computingBMI : LiveData<Boolean> = _computingBMI



    fun computeBMI() {

        _computingBMI.value = true

        Log.i("RAFA", "${Thread.currentThread().name}: Lanzando corrutina")

        CoroutineScope(Dispatchers.Default).launch {

            val deferredResult = CoroutineScope(Dispatchers.Main).async {

                Log.i(
                    "RAFA",
                    "${Thread.currentThread().name}: Llamando a computeBMI en el respositorio"
                )

                val result: Double = repository.computeBMI(weight, height)

                Log.i("RAFA", "${Thread.currentThread().name}: Llamada realizada")

                return@async result
            }

            Log.i("RAFA", "${Thread.currentThread().name}: Haciendo cosas mientras obtengo el resultado")
            val result = deferredResult.await()

            Log.i("RAFA", "${Thread.currentThread().name}: Resultado obtenido")

            withContext(Dispatchers.Main) {
                Log.i("RAFA", "${Thread.currentThread().name}: Actualizando BMI")
                _computingBMI.value = false
                _bmi.value = result
                savedStateHandle[BMI_KEY] = _bmi.value
            }
        }

        Log.i("RAFA", "${Thread.currentThread().name}:Corrutina lanzada con launch")
    }

    fun getResult(): BmiState {
        return repository.getQualitativeBMI(bmi.value!!)
    }

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