package seigneur.gauvain.chdm.ui.test.exhibition.list.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

import io.reactivex.disposables.CompositeDisposable
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import seigneur.gauvain.chdm.data.repository.ApiTestRepository

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class ExhibitionDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val mShotRepository: ApiTestRepository) : DataSource.Factory<Long, Exhibition>() {

    val mExhibitionDataSourceLiveData = MutableLiveData<ExhibitionsDataSource>()

    override fun create(): DataSource<Long, Exhibition> {
        val mExhibitionDataSource = ExhibitionsDataSource(compositeDisposable, mShotRepository)
        mExhibitionDataSourceLiveData.postValue(mExhibitionDataSource)
        return mExhibitionDataSource
    }

}
