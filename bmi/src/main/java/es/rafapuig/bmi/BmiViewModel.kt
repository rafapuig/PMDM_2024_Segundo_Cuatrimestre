package es.rafapuig.bmi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {

    enum class BmiState {UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESITY}

    var weight: Double = 0.0
    var height: Double = 0.0

    //var bmi: Double = 0.0

    val _bmi = MutableLiveData<Double>()
    val bmi : LiveData<Double> = _bmi


    fun computeBMI() {
        _bmi.value = weight / (height / 100 * height / 100)
    }

    fun getResult(): BmiState {
        if (bmi.value!! < 18.5) return BmiState.UNDERWEIGHT
        if (bmi.value!! < 25) return BmiState.NORMAL
        if (bmi.value!! < 30) return BmiState.OVERWEIGHT
        return BmiState.OBESITY
    }

}