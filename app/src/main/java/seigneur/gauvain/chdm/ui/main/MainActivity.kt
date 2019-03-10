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
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.data.api.CooperHewittService
import seigneur.gauvain.chdm.data.repository.ApiTestRepository
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    @BindView(R.id.btn)
    lateinit var mbutton: Button

    private lateinit var mMainViewModelFactory: MainViewModelFactory

    private lateinit var mMainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        val vApiTestRepository= ApiTestRepository(CooperHewittService.create())
        mMainViewModelFactory = MainViewModelFactory(vApiTestRepository)
        mMainViewModel = ViewModelProviders.of(this, mMainViewModelFactory).get(MainViewModel::class.java)
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
