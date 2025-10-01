package fr.zunkit.motorcyclemanager.presentation.addhour.screens;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import fr.zunkit.motorcyclemanager.R
import fr.zunkit.motorcyclemanager.presentation.addhour.models.AddHour

@Composable
fun AddHourStateScreen(
    onNewHour: (AddHour) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

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
                        if (it.isEmpty() || it.matches(Regex("^[0-9]*[.,]?[0-9]*$"))) {
                            time = it
                        }
                    },
                    label = { Text(stringResource(R.string.time_in_hour)) },
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
                        text = stringResource(R.string.reset_checklist),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = {
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
                        onNewHour(AddHour(fTime, resetChecks))
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(stringResource(R.string.add_ride_time))
                }
            }
        }
    }
}