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
import dagger.android.AndroidInjection
import seigneur.gauvain.chdm.R
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @BindView(R.id.btn)
    lateinit var mbutton: Button

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mMainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
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
        mMainViewModel.getExhibitionsAndObjectsForEach()
    }

}
