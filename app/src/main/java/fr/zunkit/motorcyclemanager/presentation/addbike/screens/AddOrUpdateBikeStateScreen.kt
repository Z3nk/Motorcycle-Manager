package fr.zunkit.motorcyclemanager.presentation.addbike.screens;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.zunkit.motorcyclemanager.R
import fr.zunkit.motorcyclemanager.presentation.addbike.models.AddOrUpdateBike

@Composable
fun AddBikeStateScreen(
    bikeName: String?,
    bikeTime: Float?,
    onNewBike: (AddOrUpdateBike) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(bikeName?:"") }
    var time by remember { mutableStateOf(bikeTime?.toString()?:"") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.bike_name)) },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null
            )

            OutlinedTextField(
                value = time,
                onValueChange = {
                    if (it.isEmpty() || it.matches(Regex("^[0-9]*[.,]?[0-9]*$"))) {
                        time = it
                    }
                },
                label = { Text(stringResource(R.string.time_on_hour_meter)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null
            )

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Button(
                onClick = {
                    if (name.isBlank()) {
                        errorMessage = context.getString(R.string.name_is_mandatory)
                        return@Button
                    }
                    if (time.isBlank()) {
                        errorMessage = context.getString(R.string.time_is_mandatory)
                        return@Button
                    }
                    val fTime = time.replace(",", ".").toFloatOrNull()
                    if (fTime == null || fTime <= 0) {
                        errorMessage = context.getString(R.string.time_should_be_above_0)
                        return@Button
                    }
                    errorMessage = null
                    onNewBike(AddOrUpdateBike(name, fTime))
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(stringResource(R.string.add_dirtbike))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddBikeStateScreenPreview() {
    AddBikeStateScreen(
        bikeName = "SXF",
        bikeTime = 50.0f,
        onNewBike = { bike ->
            println("Preview: Nouvelle moto ajoutée : ${bike.name}, ${bike.time} heures")
        },
        onBackClick = {}
    )
}