package seigneur.gauvain.chdm.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_exhibition_list.*
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import seigneur.gauvain.chdm.ui.base.BaseFragment
import timber.log.Timber
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper



/**
 * Created by gse on 22/11/2017.
 */
class HomeFragment : BaseFragment() {

    val mCurrentExhib = ArrayList<Exhibition>()

    val mCurrentExhibListAdapter by lazy {
        CurrentExhibListAdapter(mParentActivity, mCurrentExhib)
    }

    lateinit var mLinearLayoutManager: LinearLayoutManager

    private val mHomeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate called")
        mHomeViewModel.getExhibitions()
    }

    override fun onCreateView(inRootView: View, inSavedInstanceState: Bundle?) {
        Timber.d("onCreateView called")

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //listen LiveData
        mLinearLayoutManager = LinearLayoutManager(mParentActivity,LinearLayoutManager.HORIZONTAL, false)
        mCurrentExhibRV.adapter = mCurrentExhibListAdapter
        mCurrentExhibRV.layoutManager =  mLinearLayoutManager

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mCurrentExhibRV)

        mHomeViewModel.mListResult.observe(this,
            Observer { it ->
                if (it.inList!=null){
                    mCurrentExhibListAdapter.addCurrentExhibitions(it.inList)
                }
                if (it.inError!=null){
                  Timber.d("onError happened ${it.inError}")
                }
            })

    }

    /**
     *
     */
    override val fragmentLayout: Int
        get() = R.layout.home_fragment

}
