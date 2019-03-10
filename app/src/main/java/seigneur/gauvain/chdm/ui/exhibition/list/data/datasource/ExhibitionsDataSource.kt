package seigneur.gauvain.chdm.ui.exhibition.list.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource


import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import seigneur.gauvain.chdm.data.repository.ApiTestRepository
import seigneur.gauvain.chdm.ui.exhibition.list.data.NetworkState
import timber.log.Timber


class ExhibitionsDataSource

    internal constructor(private val compositeDisposable: CompositeDisposable,
                         private val mShotRepository: ApiTestRepository) : PageKeyedDataSource<Long, Exhibition>() {


    /*
    * Step 1: Initialize the restApiFactory.
    * The networkState and initialLoading variables
    * are for updating the UI when data is being fetched
    * by displaying a progress bar
    */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    //Keep Completable reference for the retry event
    private var retryCompletable: Completable? = null

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, Exhibition>) {
        // ignored, since we only ever append to our initial load
    }

    /*
     * Step 2: This method is responsible to load the data initially
     * when app screen is launched for the first time.
     * We are fetching the first page data from the api
     * and passing it via the callback method to the UI.
     */
    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Long>, callback: PageKeyedDataSource.LoadInitialCallback<Long, Exhibition>) {
        Timber.d("loadInitial called")
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        //
        val exhibitionList= ArrayList<Exhibition>()
        compositeDisposable.add(
            //get the list of exhibitions
            mShotRepository.getExhibitionsForRV(1, params.requestedLoadSize)
                //flatten the list to stream each item of the ist
                .flatMapIterable {
                    it.exhibitions
                }
                //FlatMap operator to get objects for each exhibition item in the list
                .flatMap {it ->
                    //create arrayList of exhibition as we can manipulate it later with objects and images
                    exhibitionList.add(it)
                    //get objects for each exhibition in the list
                    mShotRepository.getObjects(it.id)
                        .doOnNext{
                            //add objects received and an illustration to dedicated exhibition
                            exhibObject ->
                            addObjectsAndIllustration(exhibitionList, exhibitionList.indexOf(it), exhibObject)
                        }

                }
                .subscribe(
                    { _ -> },
                    { throwable ->
                        onLoadListError(LOAD_INITIAL,throwable,params, callback,null, null)
                    },
                    {
                        onLoadListComplete(exhibitionList, LOAD_INITIAL, params, callback,null, null)
                    }

                )
        )
    }

    /*
     * Step 3: This method it is responsible for the subsequent call to load the data page wise.
     * This method is executed in the background thread
     * We are fetching the next page data from the api
     * and passing it via the callback method to the UI.
     * The "params.key" variable will have the updated value.
     */
    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Long>,
                           callback: PageKeyedDataSource.LoadCallback<Long, Exhibition>) {
        // set network value to loading.
        Timber.d("loadAfter called")
        val exhibitionList= ArrayList<Exhibition>()
        //
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            //get the list of exhibitions
            mShotRepository.getExhibitionsForRV(params.key, params.requestedLoadSize)
                //flatten the list to stream each item of the ist
                .flatMapIterable {
                    it.exhibitions
                }
                //FlatMap operator to get objects for each exhibition item in the list
                .flatMap {it ->
                    //create arrayList of exhibition as we can manipulate it later with objects and images
                    exhibitionList.add(it)
                    //get objects for each exhibition in the list
                    mShotRepository.getObjects(it.id)
                        .doOnNext{
                            //add objects received and an illustration to dedicated exhibition
                            exhibObject ->
                            addObjectsAndIllustration(exhibitionList, exhibitionList.indexOf(it), exhibObject)
                        }
                }
                .subscribe(
                    { _ -> },
                    { throwable ->
                        onLoadListError(LOAD_AFTER,throwable,null, null,params, callback)
                    },
                    {
                        onLoadListComplete(exhibitionList, LOAD_AFTER, null,null, params,callback)
                    }

                )
        )
    }

    /**
     * Loading list operation is complete, perform some operations for UI
     */
    private fun onLoadListComplete(exhibitionList:ArrayList<Exhibition>,
                                   loadMode:Int,
                                   paramsInitial: PageKeyedDataSource.LoadInitialParams<Long>?=null,
                                   callbackInitial: PageKeyedDataSource.LoadInitialCallback<Long, Exhibition>?=null,
                                   paramsAfter: PageKeyedDataSource.LoadParams<Long>?=null,
                                   callbackAfter: PageKeyedDataSource.LoadCallback<Long, Exhibition>?=null) {
        when(loadMode) {
            LOAD_INITIAL-> {
                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                initialLoad.postValue(NetworkState.LOADED)
                callbackInitial!!.onResult(exhibitionList, null, 2L)

            }
            LOAD_AFTER -> {
                val nextKey = paramsAfter!!.key + 1
                // clear retry since last request succeeded
                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                callbackAfter!!.onResult(exhibitionList, nextKey)
            }
        }
    }

    /**
     * An error happened during the loading, mange it according to the the type of LOADING (INITIAL OR AFTER)
     */
    private fun onLoadListError(loadMode:Int,
                                throwable: Throwable,
                                paramsInitial: PageKeyedDataSource.LoadInitialParams<Long>?=null,
                                callbackInitial: PageKeyedDataSource.LoadInitialCallback<Long, Exhibition>?=null,
                                paramsAfter: PageKeyedDataSource.LoadParams<Long>?=null,
                                callbackAfter: PageKeyedDataSource.LoadCallback<Long, Exhibition>?=null) {

        when(loadMode) {
            LOAD_INITIAL-> {
                // keep a Completable for future retry
                setRetry(Action { loadInitial(paramsInitial!!, callbackInitial!!) })
                val error = NetworkState.error(throwable.message)
                // publish the error
                networkState.postValue(error)
                initialLoad.postValue(error)
            }
            LOAD_AFTER -> {
                // keep a Completable for future retry
                setRetry(Action { loadAfter(paramsAfter!!, callbackAfter!!) })
                //publish the error
                networkState.postValue(NetworkState.error(throwable.message))
            }
        }

    }


    /**
     * add objects fetched to each item of exhibition list
     * @param mExhibitions  -   list of exhibitions
     * @param pos           -   position of each item of the list of exhibitions
     * @param exhibObjects  -   exhibitions objects fetched
     */
    private fun addObjectsAndIllustration(mExhibitions: ArrayList<Exhibition>, pos:Int, exhibObjects :Exhibition.ExhibitionObjects) {
        //add objects received to each exhibition of the list
        mExhibitions[pos].exhibObjects = exhibObjects
        //add an illustration (if available) to each exhibition of the list
        mExhibitions[pos].illustrationUrl = getAnIllustrationPerExhibition(mExhibitions,pos)
        Timber.d("image added :"+  mExhibitions[pos].id + ": "+  mExhibitions[pos].illustrationUrl)
    }

    /**
     * Simple method get the first non nul url of image to make it as illustration of the Exhibition
     */
    private fun getAnIllustrationPerExhibition(mExhibitions: ArrayList<Exhibition>, pos:Int):String? {
        var imageUrl: String? = "No Image Available"
        for (i in 0 until mExhibitions[pos].exhibObjects.objects.size) {
            if (!mExhibitions[pos].exhibObjects.objects[i].imgList.isEmpty()) {
                imageUrl = mExhibitions[pos].exhibObjects.objects[i].firstImageUrl
            }
        }
        Timber.d("image per exhibition: "+  mExhibitions[pos].id + ": "+ imageUrl)
        return imageUrl
    }

    /**
     *
     */
    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, { throwable -> Timber.e(throwable.message) }))
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    companion object {
        val LOAD_INITIAL    = 0
        val LOAD_AFTER      = 1
    }

}
