package fr.zunkit.motorcyclemanager.presentation.addhour.screens;

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.zunkit.motorcyclemanager.R
import fr.zunkit.motorcyclemanager.design.theme.MotorcycleManagerTheme
import fr.zunkit.motorcyclemanager.presentation.addhour.models.AddHour

@Composable
fun AddHourStateScreen(
    currentBikeTime: Float,
    onNewHour: (AddHour) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                })
            }) {
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

            var timeRiding by remember { mutableStateOf("") }
            var timeBike by remember { mutableStateOf("") }
            var errorTimeRiding by remember { mutableStateOf<String?>(null) }
            var errorBikeTime by remember { mutableStateOf<String?>(null) }
            var resetChecks by remember { mutableStateOf(true) }


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
                    text = stringResource(R.string.add_hours_examples),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = timeRiding,
                    onValueChange = {
                        if (it.isBlank() || it.matches(Regex("^[0-9]*[.,]?[0-9]*$"))) {
                            timeRiding = it
                        }
                    },
                    label = { Text(stringResource(R.string.time_in_hour)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorTimeRiding != null,
                    enabled = timeBike.isBlank()
                )

                errorTimeRiding?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Text(
                    text = stringResource(R.string.or),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                OutlinedTextField(
                    value = timeBike,
                    onValueChange = {
                        if (it.isBlank() || it.matches(Regex("^[0-9]*[.,]?[0-9]*$"))) {
                            timeBike = it
                        }
                    },
                    label = { Text(stringResource(R.string.time_of_the_bike)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorBikeTime != null,
                    enabled = timeRiding.isBlank()
                )

                errorBikeTime?.let {
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

                if (!timeRiding.isBlank() || !timeBike.isBlank()) {
                    Text(
                        text = if (timeRiding.isBlank()) stringResource(
                            R.string.your_bike_has_X_hours_now,
                            currentBikeTime,
                            timeBike,
                            timeBike.replace(",", ".").toFloatOrNull()?.minus(currentBikeTime)
                                ?: 0.0f
                        ) else stringResource(R.string.you_rode_X_hours_last_ride, timeRiding, currentBikeTime),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Button(
                    onClick = {
                        if (timeRiding.isBlank() && timeBike.isBlank()) {
                            errorTimeRiding = context.getString(R.string.time_is_mandatory)
                            errorBikeTime = context.getString(R.string.time_is_mandatory)
                            return@Button
                        }
                        val fTimeRiding = timeRiding.replace(",", ".").toFloatOrNull()
                        val fTimeBike = timeBike.replace(",", ".").toFloatOrNull()
                        if (!timeRiding.isBlank()) {
                            if (fTimeRiding == null || fTimeRiding <= 0) {
                                errorTimeRiding = context.getString(R.string.time_should_be_above_0)
                                return@Button
                            }
                            errorTimeRiding = null
                            onNewHour(AddHour(fTimeRiding, resetChecks, true))
                        }
                        if (!timeBike.isBlank()) {
                            if (fTimeBike == null || fTimeBike <= 0) {
                                errorBikeTime = context.getString(R.string.time_should_be_above_0)
                                return@Button
                            }

                            if (fTimeBike < currentBikeTime) {
                                errorBikeTime =
                                    context.getString(R.string.new_time_cannot_be_less_than_current)
                                return@Button
                            }

                            errorBikeTime = null
                            onNewHour(AddHour(fTimeBike, resetChecks, false))
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(stringResource(R.string.add_ride_time))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MotorcycleManagerTheme {
        AddHourStateScreen(currentBikeTime = 0.0f, onNewHour = { _ -> }, onBackClick = {})
    }
}