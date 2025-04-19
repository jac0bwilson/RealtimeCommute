package uk.jacobw.commute.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import uk.jacobw.commute.feature.home.HomeViewModel
import uk.jacobw.commute.getPlatform

val appModule = module {
    single { getPlatform() }
    viewModel { HomeViewModel() }
}