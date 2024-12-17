package es.rafapuig.bmi.data

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay

class Repository {

    suspend fun computeBMI(weight: Double, height: Double) : Double {
        delay(4_000)
        //return 12345.0
        return weight / (height / 100 * height / 100)
    }

    fun getQualitativeBMI(bmi: Double): BmiState {
        if (bmi < 18.5) return BmiState.UNDERWEIGHT
        if (bmi < 25) return BmiState.NORMAL
        if (bmi < 30) return BmiState.OVERWEIGHT
        return BmiState.OBESITY
    }

}