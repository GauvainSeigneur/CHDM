package seigneur.gauvain.chdm.ui.objects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import seigneur.gauvain.chdm.data.model.chdmObject.ChdmObject
import seigneur.gauvain.chdm.data.model.chdmObject.ChdmObjectResponse
import seigneur.gauvain.chdm.data.repository.ObjectRepository
import timber.log.Timber

class ObjectViewModel(var mObjectRepository:ObjectRepository) : ViewModel() {

    private var mObjectMutable = MutableLiveData<ChdmObject>()

    private var isAlreadyAsking=false

    val mObject: LiveData<ChdmObject>
        get() = mObjectMutable

    private val mCompositeDisposable = CompositeDisposable()

    public override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    /**
     *
     */
    fun init() {
        if (mObjectMutable.value ==null)
            askForRandomObject()
    }

    /**
     *
     */
    fun askForRandomObject() {
      if (!isAlreadyAsking) {
          mCompositeDisposable.add(
              mObjectRepository.getRandomObject()
                  .subscribe(
                      this::onObjectFetch,
                      this::onErrorHappened
                  )

          )
      } else {
          Timber.d("Easy, isAlreadyAsking")
      }
      isAlreadyAsking=true
    }

    private fun onObjectFetch(inObject: ChdmObjectResponse?) {
        isAlreadyAsking=false
        val vChdmObject = inObject?.theObject
        Timber.d("onObjectFetch ${inObject.toString()}")
        mObjectMutable.postValue(vChdmObject)
    }

    private fun onErrorHappened(t: Throwable) {
        isAlreadyAsking=false
        Timber.d(t)
    }

}
