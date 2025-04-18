package uk.jacobw.commute

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(configure = {}) {
    App()
}