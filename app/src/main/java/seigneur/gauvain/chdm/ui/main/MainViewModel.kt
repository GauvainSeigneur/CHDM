package seigneur.gauvain.chdm.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList

import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import seigneur.gauvain.chdm.data.api.CooperHewittService
import seigneur.gauvain.chdm.data.model.exhibition.ExhibitionList
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import timber.log.Timber
import seigneur.gauvain.chdm.data.repository.ApiTestRepository

class MainViewModel(var mApiTestRepository:ApiTestRepository) : ViewModel() {

    //lateinit var mApiTestRepository:ApiTestRepository

    private val mCompositeDisposable= CompositeDisposable()
    private val hoursMutableLiveData = MutableLiveData<Response<Void>>()


    //List of exhibitions
    private var mExhibitions= ArrayList<Exhibition>()
    private var mExhibitionsMutableLiveData = MutableLiveData<ArrayList<Exhibition>>()

    val exhibitionList: LiveData<ArrayList<Exhibition>>
        get() = mExhibitionsMutableLiveData

    public override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    /*
    *********************************************************************************************
    * PUBLIC METHODS CALLED IN Fragment
    *********************************************************************************************/
    fun init() {
       // mApiTestRepository = ApiTestRepository(mCooperHewittService)
    }

    fun getExhibitionsAndObjectsForEach() {
        mCompositeDisposable.add(
            mApiTestRepository.getExhibitions()
                ?.flatMapIterable {
                    Timber.d("we get hexib $it")
                    it.exhibitions
                }
                ?.flatMap {it ->
                    mExhibitions.add(it) //create list of exhibition
                    //mApiTestRepository.getObjects(mExhibitions[mExhibitions.indexOf(it)].id)
                    mApiTestRepository.getObjects(it.id)
                        .doOnNext{
                                exhibObject -> addObjectsAndIllustration(mExhibitions.indexOf(it), exhibObject)
                        }
                }
                !!.subscribe(
                this::onListReceived,
                this::onErrorHappened,
                this::onFlatMapComplete
            )
        )
    }

    private fun addObjectsAndIllustration(pos:Int, exhibObjects :Exhibition.ExhibitionObjects) {
        //add objects received to each exhibition of the list
        mExhibitions[pos].exhibObjects = exhibObjects
        //add an illustration (if available) to each exhibition of the list
        mExhibitions[pos].illustrationUrl = getAnIllustrationPerExhibition(pos)
    }

    private fun getAnIllustrationPerExhibition(pos:Int):String? {
        var imageUrl: String? = "No Image Available"
        for (i in 0 until mExhibitions[pos].exhibObjects.objects.size) {
            if (!mExhibitions[pos].exhibObjects.objects[i].imgList.isEmpty()) {
                imageUrl = mExhibitions[pos].exhibObjects.objects[i].firstImageUrl
            }
        }
        Timber.d("image per exhibition: "+  mExhibitions[pos].id + ": "+ imageUrl)
        return imageUrl
    }

    private fun onListReceived(chdmObjectList: Exhibition.ExhibitionObjects) {
        Timber.d("object founds : " + chdmObjectList)

    }

    private fun onFlatMapComplete() {
        //warn subscribers that the list has changed
        mExhibitionsMutableLiveData.value = mExhibitions
    }

    fun getObjects(id:Long) {
        mCompositeDisposable.add(
            mApiTestRepository.getObjects(id)
                .subscribe(
                    this::onObjectsFound,
                    this::onErrorHappened
                )
        )
    }

    private fun onObjectsFound(exhibitions: Exhibition.ExhibitionObjects) {
        Timber.d(exhibitions.toString())
    }

    /**
     * get exhibitions
     */
    fun getExhibitions() {
        mCompositeDisposable.add(
            mApiTestRepository.getExhibitions()
                !!.subscribe(
                    this::onExhibitionsFound,         //User found - display info
                    this::onErrorHappened    //Error happened during the request
                )
        )
    }

    private fun onExhibitionsFound(exhibitions: ExhibitionList) {
        Timber.d("exhibitions founds : " + exhibitions)
    }

    /**
     * get opening hours of Cafe
     */
    fun getHours() {
        mCompositeDisposable.add(
                mApiTestRepository.getHours()
                        !!.subscribe(
                                this::onResponse,         //User found - display info
                                this::onErrorHappened    //Error happened during the request
                        )
        )
    }

    private fun onResponse(response: Response<Void>) {
        Timber.d("reponse " + response.body())

        val headerList = response.headers()
        Timber.d("header " + headerList)

    }

    /**
     * An error happened during the request, warn the user
     * @param t - Throwable
     */
    private fun onErrorHappened(t: Throwable) {
        Timber.d(t)
    }

    companion object {

        private val pageSize = 10
    }


}
