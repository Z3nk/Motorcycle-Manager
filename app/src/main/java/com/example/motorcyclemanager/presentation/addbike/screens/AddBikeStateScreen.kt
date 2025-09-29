package com.example.motorcyclemanager.presentation.addbike.screens;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import com.example.motorcyclemanager.R
import com.example.motorcyclemanager.presentation.addbike.models.BikeAdded

@Composable
fun AddBikeStateScreen(
    onNewBike: (BikeAdded) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    time = it
                }
            },
            label = { Text(stringResource(R.string.time_in_hour)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                val fTime = time.toFloatOrNull()
                if (fTime == null || fTime <= 0) {
                    errorMessage = context.getString(R.string.time_should_be_above_0)
                    return@Button
                }
                errorMessage = null
                onNewBike(BikeAdded(name, fTime))
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.add_dirtbike))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddBikeStateScreenPreview() {
    AddBikeStateScreen(
        onNewBike = { bike ->
            println("Preview: Nouvelle moto ajoutée : ${bike.name}, ${bike.time} heures")
        }
    )
}