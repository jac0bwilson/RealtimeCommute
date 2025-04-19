package uk.jacobw.commute.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayout(
    onNavigateToSample: () -> Unit,
    from: String,
    to: String,
    updateFrom: (String) -> Unit,
    updateTo: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Realtime Commute") },
                actions = {
                    IconButton(
                        onClick = onNavigateToSample
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Sample code"
                        )
                    }
                }
            )
        },
    ) { internalPadding ->
        Column(
            modifier = Modifier
                .padding(internalPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            JourneyInput(
                from = from,
                to = to,
                updateFrom = updateFrom,
                updateTo = updateTo,
            )
        }
    }
}

@Composable
private fun JourneyInput(
    from: String,
    to: String,
    updateFrom: (String) -> Unit,
    updateTo: (String) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Where are you going today?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = from,
                onValueChange = updateFrom,
                label = { Text("From") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                )
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = to,
                onValueChange = updateTo,
                label = { Text("To") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                )
            )
        }
    }
}