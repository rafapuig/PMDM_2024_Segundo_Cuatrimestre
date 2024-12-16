package es.rafapuig.coroutinedemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class MainViewModel : ViewModel() {

    private val _count : MutableLiveData<Int> = MutableLiveData(1)
    val count : LiveData<Int> = _count

    fun setCount(value: Int) {
        _count.value = value
    }

    fun performTaskAsync(taskNumber: Int) : Deferred<String> {
        return CoroutineScope(Dispatchers.Default).async {
            //Log.i("RAFA","${Thread.currentThread().name} esta realizando la tarea" )
            delay(5_000)
            return@async "Finished Coroutine $taskNumber"
        }
    }

}