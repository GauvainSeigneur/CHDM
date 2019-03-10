package seigneur.gauvain.chdm.di

import org.koin.dsl.module.module
import seigneur.gauvain.chdm.data.repository.ApiTestRepository


    //Single module
    val repoModule =module {

        // single instance of HelloRepository
        single {
            ApiTestRepository(get())
        }
    }






