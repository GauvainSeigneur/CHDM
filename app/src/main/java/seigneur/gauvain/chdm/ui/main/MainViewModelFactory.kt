package seigneur.gauvain.chdm.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import seigneur.gauvain.chdm.data.repository.ApiTestRepository


/**
 * Factory for ViewModels
 */
class MainViewModelFactory(private val mApiTestRepository : ApiTestRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApiTestRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}