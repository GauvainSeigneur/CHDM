package seigneur.gauvain.chdm.di.modules
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import seigneur.gauvain.chdm.ui.main.MainViewModel
import seigneur.gauvain.chdm.data.viewModel.FactoryViewModel
import seigneur.gauvain.chdm.di.scope.ViewModelKey
import seigneur.gauvain.chdm.ui.exhibition.ExhibitionListViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExhibitionListViewModel::class)
    internal abstract fun bindExhibitionListViewModel(exhibitionListViewModel: ExhibitionListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: FactoryViewModel): ViewModelProvider.Factory

}
