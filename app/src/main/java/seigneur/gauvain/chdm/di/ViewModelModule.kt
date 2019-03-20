package seigneur.gauvain.chdm.di

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import seigneur.gauvain.chdm.ui.objects.ObjectViewModel
import seigneur.gauvain.chdm.ui.test.exhibition.ExhibitionListViewModel
import seigneur.gauvain.chdm.ui.test.TestViewModel

   //Single module
    val viewModelModule =module {

    // single instance of HelloRepository
    viewModel {
            TestViewModel(get())
        }

    viewModel {
        ExhibitionListViewModel(get())
        }

       viewModel {
           ObjectViewModel(get())
       }


   }






