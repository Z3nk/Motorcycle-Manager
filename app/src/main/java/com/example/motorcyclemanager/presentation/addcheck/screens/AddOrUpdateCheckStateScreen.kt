package com.example.motorcyclemanager.presentation.addcheck.screens;

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.motorcyclemanager.R
import com.example.motorcyclemanager.presentation.addcheck.models.AddOrUpdateCheck

@Composable
fun AddCheckStateScreen(
    checkName: String?,
    onNewCheck: (AddOrUpdateCheck) -> Unit
) {
    var name by remember { mutableStateOf(checkName?:"") }
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
            label = { Text("Nom du point de controle") },
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
                    errorMessage = "Le nom est requis"
                    return@Button
                }
                errorMessage = null
                onNewCheck(AddOrUpdateCheck(name))
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.validate))
        }
    }

}