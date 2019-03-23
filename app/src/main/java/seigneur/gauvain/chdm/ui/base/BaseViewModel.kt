package seigneur.gauvain.chdm.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base ViewModel
 * handle Rx jobs with launch() and clear them on onCleared
 */
abstract class BaseViewModel : ViewModel() {

    val mDisposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        mDisposables.add(job())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        mDisposables.clear()
    }
}

