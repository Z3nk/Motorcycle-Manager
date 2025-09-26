package com.example.motorcyclemanager.presentation.addhour.screens;

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import com.example.motorcyclemanager.presentation.addhour.models.AddHour

@Composable
fun AddHourStateScreen(
    onNewHour: (AddHour) -> Unit
) {
    var time by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        var time by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        var resetChecks by remember { mutableStateOf(true) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

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

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Checkbox(
                    checked = resetChecks,
                    onCheckedChange = { resetChecks = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Remettre à 0 tous les points de contrôle",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = {
                    if (time.isBlank()) {
                        errorMessage = "Le temps est requis"
                        return@Button
                    }
                    val fTime = time.toFloatOrNull()
                    if (fTime == null || fTime <= 0) {
                        errorMessage = "Le temps doit être un nombre positif"
                        return@Button
                    }
                    errorMessage = null
                    onNewHour(AddHour(fTime, resetChecks))
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Ajouter les heures de roulage")
            }
        }    }

}