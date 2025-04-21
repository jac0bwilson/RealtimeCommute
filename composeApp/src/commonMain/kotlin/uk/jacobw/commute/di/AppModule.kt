package uk.jacobw.commute.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import uk.jacobw.commute.data.RouteRepository
import uk.jacobw.commute.data.StationRepository
import uk.jacobw.commute.data.database.RouteDatabase
import uk.jacobw.commute.data.network.station.StationApi
import uk.jacobw.commute.feature.home.HomeViewModel
import uk.jacobw.commute.getPlatform

val appModule = module {
    single { getPlatform() }

    single { get<RouteDatabase>().getDao() }
    single { RouteRepository(get()) }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
        }
    }
    single { StationApi(get()) }
    single { StationRepository(get()) }

    viewModel { HomeViewModel(get(), get()) }
}