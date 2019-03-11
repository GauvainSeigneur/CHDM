package seigneur.gauvain.chdm.ui.main

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.data.api.CooperHewittService
import seigneur.gauvain.chdm.data.repository.ApiTestRepository
import timber.log.Timber

class MainActivity : AppCompatActivity() {


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



    fun getExhibition() {
        mMainViewModel?.getHours()
    }

}
