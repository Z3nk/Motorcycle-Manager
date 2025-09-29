package com.example.motorcyclemanager.presentation.addconsumable.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.example.motorcyclemanager.R
import com.example.motorcyclemanager.presentation.addconsumable.models.AddOrUpdateConsumable

@Composable
fun AddOrUpdateConsumableStateScreen(
    consumableName: String?,
    consumableTime: Float?,
    consumableCurrentTime: Float?,
    onNewConsumable: (AddOrUpdateConsumable) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(consumableName ?: "") }
    var time by remember { mutableStateOf(consumableTime?.toString() ?: "") }
    var currentTime by remember { mutableStateOf(consumableCurrentTime?.toString()) }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.consumables_examples),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.name_consumable)) },
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

            currentTime?.let { lCurrentTime ->
                OutlinedTextField(
                    value = lCurrentTime,
                    onValueChange = {
                        if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                            currentTime = it
                        }
                    },
                    label = { Text(stringResource(R.string.time_already_spent)) },
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

                    val currentTime = currentTime?.toFloatOrNull()
                    if (consumableCurrentTime != null) {
                        if (currentTime == null || currentTime < 0) {
                            errorMessage = context.getString(R.string.time_should_not_be_negative)
                            return@Button
                        }
                    }

                    errorMessage = null
                    onNewConsumable(AddOrUpdateConsumable(name, fTime, currentTime ?: 0.0f))
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(stringResource(R.string.add_consumable))
            }
        }
    }
}