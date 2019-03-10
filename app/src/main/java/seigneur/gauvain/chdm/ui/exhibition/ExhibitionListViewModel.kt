package seigneur.gauvain.chdm.ui.exhibition

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList

import io.reactivex.disposables.CompositeDisposable
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import timber.log.Timber
import seigneur.gauvain.chdm.data.repository.ApiTestRepository
import seigneur.gauvain.chdm.ui.exhibition.list.data.NetworkState
import seigneur.gauvain.chdm.ui.exhibition.list.data.datasource.ExhibitionDataSourceFactory
import seigneur.gauvain.chdm.ui.exhibition.list.data.datasource.ExhibitionsDataSource


class ExhibitionListViewModel

constructor() : ViewModel() {

    lateinit var mApiTestRepository: ApiTestRepository

    private val mCompositeDisposable = CompositeDisposable()

    public override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    private val exhibitionDataSourceFactory: ExhibitionDataSourceFactory by lazy {
        ExhibitionDataSourceFactory(mCompositeDisposable, mApiTestRepository)
    }

    /**
     * Configures how a PagedList loads content from its DataSource.
     * <p>
     * Use a Config {@link Builder} to construct and define custom loading behavior, such as
     * {@link Builder#setPageSize(int)}, which defines number of items loaded at a time}.
     */
    private var config: PagedList.Config? = null

    /**
     * List that view subscribes to display change
     */
    var mExhibitions: LiveData<PagedList<Exhibition>>? = null

    /**
     * Network State that view subscribes
     */
    val networkState: LiveData<NetworkState>
        get() =  Transformations.switchMap(exhibitionDataSourceFactory.mExhibitionDataSourceLiveData) {
            it.networkState
        }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(exhibitionDataSourceFactory.mExhibitionDataSourceLiveData,
            Function<ExhibitionsDataSource, LiveData<NetworkState>> { it.initialLoad })

    /*
    *********************************************************************************************
    * PUBLIC METHODS CALLED IN Fragment
    *********************************************************************************************/
    fun init() {
        if (config == null && mExhibitions == null) {
            config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(false)
                .build()
            mExhibitions = LivePagedListBuilder(exhibitionDataSourceFactory, config!!).build()
        }
    }


    fun retry() {
        if (exhibitionDataSourceFactory.mExhibitionDataSourceLiveData.value != null)
            exhibitionDataSourceFactory.mExhibitionDataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        if (exhibitionDataSourceFactory.mExhibitionDataSourceLiveData.value != null)
            exhibitionDataSourceFactory.mExhibitionDataSourceLiveData.value!!.invalidate()
    }

    companion object {
        private val pageSize = 10
    }

}
