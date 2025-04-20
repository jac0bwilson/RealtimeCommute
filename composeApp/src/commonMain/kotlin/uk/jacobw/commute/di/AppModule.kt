package uk.jacobw.commute.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import uk.jacobw.commute.data.RouteRepository
import uk.jacobw.commute.data.database.RouteDatabase
import uk.jacobw.commute.feature.home.HomeViewModel
import uk.jacobw.commute.getPlatform

val appModule = module {
    single { getPlatform() }
    single { get<RouteDatabase>().getDao() }
    single { RouteRepository(get()) }
    viewModel { HomeViewModel(get()) }
}