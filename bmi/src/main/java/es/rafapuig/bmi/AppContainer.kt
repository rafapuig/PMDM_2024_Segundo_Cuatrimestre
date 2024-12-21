package es.rafapuig.bmi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.bmi.data.RepositoryImpl
import es.rafapuig.bmi.domain.Repository
import es.rafapuig.bmi.ui.BmiViewModel
import kotlin.reflect.KClass

class AppContainer {

    val repository : Repository = RepositoryImpl()

    /*val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(
            modelClass: KClass<T>,
            extras: CreationExtras): T {

            val application = checkNotNull(extras[APPLICATION_KEY])
            val savedStateHandle = extras.createSavedStateHandle()

            return BmiViewModel(application) as T
        }

    }*/

    val BmiViewModelFactory = viewModelFactory {
        initializer {
            val savedStateHandle = createSavedStateHandle()
            val application = (this[APPLICATION_KEY] as BmiApplication)
            val repository = application.appContainer.repository
            BmiViewModel(repository, savedStateHandle)
        }
    }

}