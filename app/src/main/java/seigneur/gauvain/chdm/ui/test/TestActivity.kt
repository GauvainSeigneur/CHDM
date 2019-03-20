package seigneur.gauvain.chdm.ui.test


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.data.repository.ApiTestRepository


class TestActivity : AppCompatActivity() {


    private lateinit var mTestViewModelFactory: TestViewModelFactory

   // private lateinit var mMainViewModel: TestViewModel

    // Lazy injected Presenter instance
    private val mApiTestRepository : ApiTestRepository by inject()


    /*
    * Declare TestViewModel with Koin and allow constructor dependency injection
    */
    private val mTestViewModel by viewModel<TestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        //val vApiTestRepository= ApiTestRepository()
        //mTestViewModelFactory = TestViewModelFactory(mApiTestRepository)
        //mMainViewModel = ViewModelProviders.of(this, mTestViewModelFactory).get(TestViewModel::class.java)
        mTestViewModel.init()
    }






}
