package seigneur.gauvain.chdm.ui.home

import android.os.Bundle
import android.view.View
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.ui.base.BaseFragment
import timber.log.Timber

/**
 * Created by gse on 22/11/2017.
 */
class HomeFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate called")
    }

    override fun onCreateView(inRootView: View, inSavedInstanceState: Bundle?) {
        Timber.d("onCreateView called")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //listen LiveData

    }

    override val fragmentLayout: Int
        get() = R.layout.home_fragment


}
