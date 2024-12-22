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

fun Double?.toString(context: Context): String = context.getString(toStringResource(), this)
/*with(context) {
    //if (this@toString?.isFinite() == true) getString(R.string.format_bmi, this@toString)
    this@toString?.let { if(it.isFinite()) getString(R.string.format_bmi, it) else getString(R.string.empty)} ?: getString(R.string.empty)
    //else getString(R.string.empty)
    /*this@toString?.let { when {
        !it.isFinite() -> getString(R.string.empty)
        else -> getString(R.string.format_bmi, it)
    }
    } ?: getString(R.string.empty)*/
}*/


@StringRes
fun Double?.toStringResource(): Int =
    this?.let { if(it.isFinite()) R.string.format_bmi else R.string.non_valid } ?: R.string.empty
    //if (this?.isFinite() == true) R.string.format_bmi else R.string.empty

