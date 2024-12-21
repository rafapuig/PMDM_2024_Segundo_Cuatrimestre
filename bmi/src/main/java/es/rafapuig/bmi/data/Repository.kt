package es.rafapuig.bmi.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class Repository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun computeBMI(weight: Double, height: Double) : Deferred<Double> {
        return coroutineScope.async {
            delay(1_000.milliseconds)
            return@async weight / (height / 100 * height / 100)
        }
    }

    fun getQualitativeBMI(bmi: Double): BmiState {
        if (bmi < 18.5) return BmiState.UNDERWEIGHT
        if (bmi < 25) return BmiState.NORMAL
        if (bmi < 30) return BmiState.OVERWEIGHT
        return BmiState.OBESITY
    }

}