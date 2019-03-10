package seigneur.gauvain.chdm.ui.exhibition

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

import io.reactivex.disposables.CompositeDisposable
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import timber.log.Timber
import seigneur.gauvain.chdm.data.repository.ApiTestRepository
import seigneur.gauvain.chdm.ui.exhibition.list.data.NetworkState
import seigneur.gauvain.chdm.ui.exhibition.list.data.datasource.ExhibitionDataSourceFactory
import seigneur.gauvain.chdm.ui.exhibition.list.data.datasource.ExhibitionsDataSource


class ExhibitionListViewModel

constructor(val mApiTestRepository: ApiTestRepository) : ViewModel() {


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
