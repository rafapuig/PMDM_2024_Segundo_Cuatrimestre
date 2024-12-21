package es.rafapuig.bmi.domain

import es.rafapuig.bmi.data.BmiState

interface Repository {

    suspend fun computeBMI(weight: Double, height: Double) : Double

    fun getQualitativeBMI(bmi: Double): BmiState

}