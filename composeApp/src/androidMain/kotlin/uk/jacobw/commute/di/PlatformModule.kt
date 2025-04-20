package uk.jacobw.commute.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import uk.jacobw.commute.data.database.getRouteDatabase
import uk.jacobw.commute.getDatabaseBuilder

actual val platformModule: Module = module {
    single {
        val builder = getDatabaseBuilder(context = androidContext())
        getRouteDatabase(builder)
    }
}