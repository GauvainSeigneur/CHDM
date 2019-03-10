package seigneur.gauvain.chdm.di

import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import seigneur.gauvain.chdm.ui.main.MainViewModel


//Single module
    val viewModelModule =module {

        // single instance of HelloRepository
    viewModel {
            MainViewModel(get())
        }
    }






