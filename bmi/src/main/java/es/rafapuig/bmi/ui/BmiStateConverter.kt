package es.rafapuig.bmi.ui

import android.content.Context
import androidx.annotation.StringRes
import es.rafapuig.bmi.R
import es.rafapuig.bmi.data.BmiState
import es.rafapuig.bmi.data.BmiState.*

@StringRes
fun BmiState?.toStringResource(): Int {
    return when (this) {
        UNDERWEIGHT -> R.string.underweight
        NORMAL -> R.string.normal
        OVERWEIGHT -> R.string.overweight
        OBESITY -> R.string.obesity
        null -> R.string.empty
    }
}

fun Double?.toString(context: Context): String = with(context) {
    this@toString?.let { when {
        it.isNaN() -> getString(R.string.empty)
        else -> getString(R.string.format_bmi, it)
    }
    } ?: getString(R.string.empty)
}


@StringRes
fun Double?.toStringResource(): Int = if (this != null) R.string.format_bmi else R.string.empty

