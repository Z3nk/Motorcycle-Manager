package fr.zunkit.motorcyclemanager.presentation.home.screens.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.zunkit.motorcyclemanager.R
import fr.zunkit.motorcyclemanager.presentation.home.HomeScreenUiState
import fr.zunkit.motorcyclemanager.presentation.home.composables.BikeCard
import fr.zunkit.motorcyclemanager.presentation.home.models.BikeWithConsumableAndChecks

@Composable
fun HomeTab(
    modifier: Modifier,
    onNavigateToAddBike: () -> Unit,
    homeScreenState: HomeScreenUiState.HomeScreenState,
    onNavigateToBikeDetail: (Long) -> Unit,
    onNavigateToEditBike: (BikeWithConsumableAndChecks) -> Unit,
    onDeleteBike: (BikeWithConsumableAndChecks) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge
        )

        Button(
            onClick = { onNavigateToAddBike() },
            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            content = {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_a_dirtbike)
                    )
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.add_a_dirtbike),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            })


        homeScreenState.bikeList?.let { lBikeList ->
            LazyColumn(modifier = Modifier) {
                items(lBikeList.size) { index ->
                    BikeCard(
                        lBikeList[index],
                        onClick = {
                            onNavigateToBikeDetail(lBikeList[index].id)
                        },
                        onEditClick = {
                            onNavigateToEditBike(lBikeList[index])
                        },
                        onDeleteCLick = {
                            onDeleteBike(lBikeList[index])
                        })
                }
            }
        }
    }
}
