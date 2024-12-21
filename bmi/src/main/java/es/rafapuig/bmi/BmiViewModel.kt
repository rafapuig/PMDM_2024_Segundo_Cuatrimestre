package es.rafapuig.bmi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.rafapuig.bmi.data.BmiState
import es.rafapuig.bmi.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Thread.currentThread

class BmiViewModel : ViewModel() {

    final val TAG = "BMI"

    private val repository = Repository()

    var weight: Double = 0.0
    var height: Double = 0.0

    //var bmi: Double = 0.0

    private val _bmi = MutableLiveData<Double>()
    val bmi: LiveData<Double> = _bmi

    private val _computingBMI = MutableLiveData<Boolean>(false)
    val computingBMI: LiveData<Boolean> = _computingBMI


    fun computeBMI() {

        _computingBMI.value = true

        Log.i(TAG, "${currentThread().name}: Llamando a computeBMI")
        val deferredResult = repository.computeBMI(weight, height)
        Log.i(TAG, "${currentThread().name}: Llamada realizada")

        Log.i(TAG, "${currentThread().name}: Lanzando corrutina")
        viewModelScope.launch {
            Log.i(
                TAG,
                "${currentThread().name}: Haciendo otras cosas mientras obtengo el resultado"
            )

            val result = deferredResult.await()
            Log.i(TAG, "${Thread.currentThread().name}: Resultado obtenido")

            withContext(Dispatchers.Main) {
                Log.i("RAFA", "${Thread.currentThread().name}: Actualizando BMI")
                _bmi.value = result
                _computingBMI.value = false
            }
        }

        Log.i(TAG, "${currentThread().name}: Corrutina lanzada con launch")
    }


    fun getResult(): BmiState {
        return repository.getQualitativeBMI(bmi.value!!)
    }
}