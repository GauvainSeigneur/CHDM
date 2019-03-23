package seigneur.gauvain.chdm.ui.objects

import androidx.lifecycle.MutableLiveData
import seigneur.gauvain.chdm.data.model.chdmObject.ChdmObject
import seigneur.gauvain.chdm.data.model.chdmObject.ChdmObjectResponse
import seigneur.gauvain.chdm.data.repository.ObjectRepository
import seigneur.gauvain.chdm.ui.base.BaseViewModel
import timber.log.Timber

class ObjectViewModel(private val mObjectRepository:ObjectRepository) : BaseViewModel() {

    var mResultUiModel = MutableLiveData<ResultUIModel>()

    private var mIsAlreadyAsking=false

    public override fun onCleared() {
        super.onCleared()
    }

    /**
     * Method to be called when the view is ready
     */
    fun init() {
        if (mResultUiModel.value ==null)
            askForRandomObject()
    }

    /**
     * get a random object from API
     */
    fun askForRandomObject() {
      if (!mIsAlreadyAsking) {
          launch {
              mObjectRepository.getRandomObject()
                  .subscribe(
                      this::onObjectFetch,
                      this::onErrorHappened
                  )
          }
      } else {
          Timber.d("Easy, isAlreadyAsking")
      }
        mIsAlreadyAsking =true
    }

    /**
     * We fetch a random object, warn UI
     */
    private fun onObjectFetch(inObject: ChdmObjectResponse?) {
        mIsAlreadyAsking =false
        val vChdmObject = inObject?.theObject
        mResultUiModel.value = ResultUIModel(vChdmObject)
    }

    /**
     *
     */
    private fun onErrorHappened(t: Throwable) {
        mIsAlreadyAsking =false
        mResultUiModel.value = ResultUIModel(inError = t)
        Timber.d(t)
    }

    /**
     * class dedicated to manage UI related data
     */
    data class ResultUIModel(val inChdmObject: ChdmObject?=null, val inError: Throwable? = null)

}
