package uk.jacobw.commute.di

import org.koin.dsl.module
import uk.jacobw.commute.getPlatform

val appModule = module {
    single { getPlatform() }
}