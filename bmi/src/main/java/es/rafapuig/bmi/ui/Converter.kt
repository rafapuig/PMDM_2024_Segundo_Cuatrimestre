package es.rafapuig.bmi.ui

import androidx.annotation.StringRes
import es.rafapuig.bmi.R
import es.rafapuig.bmi.data.BmiState
import es.rafapuig.bmi.data.BmiState.*

@StringRes
fun BmiState.toStringResource(): Int {
    return when (this) {
        UNDERWEIGHT -> R.string.underweight
        NORMAL -> R.string.normal
        OVERWEIGHT -> R.string.overweight
        OBESITY -> R.string.obesity
    }
}

@StringRes
fun bmiStateToStringResource(state: BmiState?): Int {
    if(state == null) return R.string.empty
    return when (state) {
        UNDERWEIGHT -> R.string.underweight
        NORMAL -> R.string.normal
        OVERWEIGHT -> R.string.overweight
        OBESITY -> R.string.obesity
    }
}