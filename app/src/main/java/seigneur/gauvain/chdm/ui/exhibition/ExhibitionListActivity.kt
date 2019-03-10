package seigneur.gauvain.chdm.ui.exhibition

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional
import org.koin.android.viewmodel.ext.android.viewModel
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import seigneur.gauvain.chdm.data.model.exhibition.ExhibitionList
import seigneur.gauvain.chdm.ui.exhibition.list.adapter.ExhibitionItemCallback
import seigneur.gauvain.chdm.ui.exhibition.list.adapter.ExhibitionListAdapter
import seigneur.gauvain.chdm.ui.exhibition.list.data.NetworkState
import seigneur.gauvain.chdm.ui.exhibition.list.data.Status
import seigneur.gauvain.chdm.ui.main.MainViewModel
import timber.log.Timber

class ExhibitionListActivity : AppCompatActivity(), ExhibitionItemCallback {

    /*
* Declare MainViewModel with Koin and allow constructor dependency injection
*/
    private val mExhibitionListViewModel by viewModel<ExhibitionListViewModel>()

    @BindView(R.id.mSwipeRefreshLayout)
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.rvShots)
    lateinit var mRvShots: RecyclerView

    @BindView(R.id.globalNetworkState)
    lateinit var globalNetworkState: LinearLayout

    @BindView(R.id.errorMessageTextView)
    lateinit var errorMessageTextView: TextView

    @BindView(R.id.retryLoadingButton)
    lateinit var retryLoadingButton: Button

    @BindView(R.id.loadingProgressBar)
    lateinit var loadingProgressBar: ProgressBar

    private val exhibitionListAdapter: ExhibitionListAdapter by lazy {
        ExhibitionListAdapter(this)
    }
    lateinit var mLinearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition_list)
        ButterKnife.bind(this)
        mExhibitionListViewModel.init()
        initAdapter()
        initSwipeToRefresh()
    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        mExhibitionListViewModel.refreshState.observe(this,
            Observer<NetworkState> { networkState ->
                if (networkState != null) {
                    if (exhibitionListAdapter.currentList != null) {
                        if (exhibitionListAdapter.currentList!!.size > 0) {
                            mSwipeRefreshLayout.isRefreshing = networkState.status == NetworkState.LOADING.status
                        } else {
                            setInitialLoadingState(networkState)
                        }
                    } else {
                        setInitialLoadingState(networkState)
                    }
                }
            })
        mSwipeRefreshLayout.setOnRefreshListener { mExhibitionListViewModel.refresh() }
    }


    private fun initAdapter() {
        if (mRvShots.layoutManager==null && mRvShots.adapter==null) {
            mLinearLayoutManager = LinearLayoutManager(this)
            mRvShots.layoutManager =  mLinearLayoutManager
            mRvShots.adapter = exhibitionListAdapter

        }

        mExhibitionListViewModel.mExhibitions?.observe(this, Observer<PagedList<Exhibition>> {exhibitionListAdapter.submitList(it)})
        mExhibitionListViewModel.networkState.observe(this, Observer<NetworkState> { exhibitionListAdapter.setNetworkState(it!!) })

    }

    private fun setInitialLoadingState(networkState: NetworkState) {
        //error message
        errorMessageTextView.visibility = if (!networkState.message.isEmpty()) View.VISIBLE else View.GONE
        errorMessageTextView.text = networkState.message
        //Visibility according to state
        retryLoadingButton.visibility = if (networkState.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState.status == Status.RUNNING) View.VISIBLE else View.GONE
        globalNetworkState.visibility = if (networkState.status == Status.SUCCESS) View.GONE else View.VISIBLE
        //set default state
        mSwipeRefreshLayout.isEnabled = networkState.status == Status.SUCCESS
    }

    @Optional
    @OnClick(R.id.retryLoadingButton)
    internal fun retryInitialLoading() {
        mExhibitionListViewModel.retry()
    }

    override fun retry() {
        mExhibitionListViewModel.retry()
    }

    override fun onItemclicked(position: Int) {
        Timber.d("clicked: "+position)
    }
}


