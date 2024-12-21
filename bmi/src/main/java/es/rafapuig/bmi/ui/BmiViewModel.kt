package es.rafapuig.bmi.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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

    private val _bmi = MutableLiveData<Double>()
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
            }
        }

        Log.i("RAFA", "${Thread.currentThread().name}:Corrutina lanzada con launch")
    }

    fun getResult(): BmiState {
        return repository.getQualitativeBMI(bmi.value!!)
    }

}