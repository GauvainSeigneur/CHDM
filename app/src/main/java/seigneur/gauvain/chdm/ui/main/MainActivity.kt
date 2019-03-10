package seigneur.gauvain.chdm.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.data.api.CooperHewittService
import seigneur.gauvain.chdm.data.repository.ApiTestRepository
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    @BindView(R.id.btn)
    lateinit var mbutton: Button

    private lateinit var mMainViewModelFactory: MainViewModelFactory

   // private lateinit var mMainViewModel: MainViewModel

    // Lazy injected Presenter instance
    private val mApiTestRepository : ApiTestRepository by inject()

    //private val mMainViewModel : MainViewModel by inject()

    /*
    * Declare MainViewModel with Koin and allow constructor dependency injection
    */
    private val mMainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        //val vApiTestRepository= ApiTestRepository()
        //mMainViewModelFactory = MainViewModelFactory(mApiTestRepository)
        //mMainViewModel = ViewModelProviders.of(this, mMainViewModelFactory).get(MainViewModel::class.java)
        mMainViewModel.init()
        subscribeToLiveData(mMainViewModel)
    }


    private fun subscribeToLiveData(viewModel: MainViewModel) {
        viewModel.
            exhibitionList.
            observe(this,
                Observer { list ->
                    for (i in 0 until list!!.size) {
                        Timber.d("activity received List:" +list[i].id+": " + list[i].illustrationUrl)
                    }

                }
            )
    }


    @OnClick(R.id.btn)
    fun getExhibition() {
        mMainViewModel?.getHours()
    }

}
