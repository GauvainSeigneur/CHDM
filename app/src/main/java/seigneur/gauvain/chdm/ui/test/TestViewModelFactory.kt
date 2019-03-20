package seigneur.gauvain.chdm.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import seigneur.gauvain.chdm.data.repository.ApiTestRepository


/**
 * Factory for ViewModels
 */
class TestViewModelFactory(private val mApiTestRepository : ApiTestRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            return TestViewModel(mApiTestRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}