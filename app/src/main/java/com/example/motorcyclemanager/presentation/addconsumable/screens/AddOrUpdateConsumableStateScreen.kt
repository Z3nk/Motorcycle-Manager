package com.example.motorcyclemanager.presentation.addconsumable.screens

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.motorcyclemanager.presentation.addconsumable.models.AddOrUpdateConsumable

@Composable
fun AddConsumableStateScreen(
    consumableName: String?,
    consumableTime: Float?,
    consumableCurrentTime: Float?,
    onNewConsumable: (AddOrUpdateConsumable) -> Unit
) {
    var name by remember { mutableStateOf(consumableName ?: "") }
    var time by remember { mutableStateOf(consumableTime?.toString() ?: "") }
    var currentTime by remember { mutableStateOf(consumableCurrentTime?.toString()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom de la conso") },
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
            label = { Text("Temps (en heures)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage != null
        )

        currentTime?.let { lCurrentTime ->
            OutlinedTextField(
                value = lCurrentTime,
                onValueChange = {
                    if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                        currentTime = it
                    }
                },
                label = { Text("Heures déjà faites") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null
            )
        }

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
                    errorMessage = "Le nom est requis"
                    return@Button
                }
                if (time.isBlank()) {
                    errorMessage = "Le temps est requis"
                    return@Button
                }
                val fTime = time.toFloatOrNull()
                if (fTime == null || fTime <= 0) {
                    errorMessage = "Le temps doit être un nombre positif"
                    return@Button
                }

                val currentTime = currentTime?.toFloatOrNull()
                if (consumableCurrentTime != null) {
                    if (currentTime == null || currentTime < 0) {
                        errorMessage = "Le temps déjà utilisé doit être un nombre non négatif"
                        return@Button
                    }
                }

                errorMessage = null
                onNewConsumable(AddOrUpdateConsumable(name, fTime, currentTime ?: 0.0f))
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Ajouter la conso")
        }
    }

}