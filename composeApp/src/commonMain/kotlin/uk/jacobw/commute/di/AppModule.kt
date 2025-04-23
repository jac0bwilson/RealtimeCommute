package uk.jacobw.commute.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import uk.jacobw.commute.BuildKonfig
import uk.jacobw.commute.data.RealtimeTrainsRepository
import uk.jacobw.commute.data.RouteRepository
import uk.jacobw.commute.data.StationRepository
import uk.jacobw.commute.data.database.RouteDatabase
import uk.jacobw.commute.data.network.realtimetrains.RealtimeTrainsApi
import uk.jacobw.commute.data.network.station.StationApi
import uk.jacobw.commute.feature.home.HomeViewModel
import uk.jacobw.commute.feature.route.RouteViewModel
import uk.jacobw.commute.getPlatform

val appModule = module {
    single { getPlatform() }

    single { get<RouteDatabase>().getDao() }
    single { RouteRepository(get()) }

    single(named("stations")) {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
        }
    }
    single { StationApi(get(named("stations"))) }
    single { StationRepository(get()) }

    single(named("realtime")) {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            install(Auth) {
                basic {
                    credentials {
                        BasicAuthCredentials(username = BuildKonfig.RTT_API_USER, password = BuildKonfig.RTT_API_KEY)
                    }
                    sendWithoutRequest { _ ->
                        true
                    }
                }
            }
        }
    }
    single { RealtimeTrainsApi(get(named("realtime"))) }
    single { RealtimeTrainsRepository(get()) }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { RouteViewModel(get(), get()) }
}