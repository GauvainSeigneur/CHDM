package seigneur.gauvain.chdm.di

import org.koin.dsl.module.module
import seigneur.gauvain.chdm.data.repository.ApiTestRepository
import seigneur.gauvain.chdm.data.repository.ExhibitionRepository
import seigneur.gauvain.chdm.data.repository.ObjectRepository

    //Single module
    val repoModule =module {

        single {
            ApiTestRepository(get())
        }

        single {
            ObjectRepository(get())
        }

        single {
            ExhibitionRepository(get())
        }
    }






