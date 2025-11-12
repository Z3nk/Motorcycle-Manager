package fr.zunkit.motorcyclemanager.presentation.bikedetails;

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.zunkit.motorcyclemanager.domain.checks.CheckCheckUseCase
import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import fr.zunkit.motorcyclemanager.domain.checks.DeleteCheckUseCase
import fr.zunkit.motorcyclemanager.domain.consumables.DeleteConsumableUseCase
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Bike
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Check
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Consumable
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.zunkit.motorcyclemanager.domain.bikes.AddInvoiceUseCase
import fr.zunkit.motorcyclemanager.domain.bikes.GetBikeUseCase
import fr.zunkit.motorcyclemanager.domain.bikes.AddPictureToBikeUseCase
import fr.zunkit.motorcyclemanager.domain.consumables.RenewConsumableUseCase
import fr.zunkit.motorcyclemanager.domain.invoices.DeleteInvoiceUseCase
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Invoice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class BikeDetailsViewModel @Inject constructor(
    private val getBikeUseCase: GetBikeUseCase,
    private val checkCheckUseCase: CheckCheckUseCase,
    private val deleteCheckUseCase: DeleteCheckUseCase,
    private val deleteConsumableUseCase: DeleteConsumableUseCase,
    private val renewConsumableUseCase: RenewConsumableUseCase,
    private val addPictureToBikeUseCase: AddPictureToBikeUseCase,
    private val addInvoiceUseCase: AddInvoiceUseCase,
    private val deleteInvoiceUseCase: DeleteInvoiceUseCase,
) :
    ViewModel() {
    private val bikeWithConsumablesAndChecksDomain =
        MutableStateFlow<BikeWithConsumablesAndChecksDomain?>(null)

    private val loading = MutableStateFlow(false)

    val uiState: StateFlow<BikeDetailsScreenUiState> = combine(
        bikeWithConsumablesAndChecksDomain,
        loading
    ) { _bikeWithConsumablesAndChecksDomain, _loading ->
        if (_loading || _bikeWithConsumablesAndChecksDomain == null) {
            BikeDetailsScreenUiState.LoadingState
        } else {
            BikeDetailsScreenUiState.BikeDetailsScreenState(
                Bike(_bikeWithConsumablesAndChecksDomain)
            )
        }
    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        BikeDetailsScreenUiState.LoadingState
    )

    fun initScreen(bikeId: Long) {
        refreshBikeWith(bikeId)
    }

    fun onDeleteCheck(check: Check) {
        viewModelScope.launch(Dispatchers.Main) {
            bikeWithConsumablesAndChecksDomain.value?.bike?.id?.let { bikeId ->
                deleteCheckUseCase(check).collectLatest { res ->
                    when (res) {
                        is Resource.Error<*> -> {}
                        is Resource.Loading<*> -> {}
                        is Resource.Success<*> -> {
                            refreshBikeWith(bikeId)
                        }
                    }
                }
            }
        }
    }

    fun onRenewConsumable(consumable: Consumable, note: String) {
        viewModelScope.launch(Dispatchers.Main) {
            bikeWithConsumablesAndChecksDomain.value?.bike?.id?.let { bikeId ->
                renewConsumableUseCase(consumable, bikeId, note).collectLatest { res ->
                    when (res) {
                        is Resource.Error<*> -> {}
                        is Resource.Loading<*> -> {}
                        is Resource.Success<*> -> {
                            refreshBikeWith(bikeId)
                        }
                    }
                }

            }
        }
    }

    fun onDeleteConsumable(consumable: Consumable) {
        viewModelScope.launch(Dispatchers.Main) {
            bikeWithConsumablesAndChecksDomain.value?.bike?.id?.let { bikeId ->
                deleteConsumableUseCase(consumable).collectLatest { res ->
                    when (res) {
                        is Resource.Error<*> -> {}
                        is Resource.Loading<*> -> {}
                        is Resource.Success<*> -> {
                            refreshBikeWith(bikeId)
                        }
                    }
                }

            }
        }
    }

    fun checkOn(check: Check) {
        viewModelScope.launch(Dispatchers.Main) {
            bikeWithConsumablesAndChecksDomain.value?.bike?.id?.let { bikeId ->
                checkCheckUseCase(bikeId, check).collectLatest { res ->
                    when (res) {
                        is Resource.Error<*> -> {}
                        is Resource.Loading<*> -> {}
                        is Resource.Success<*> -> {
                            refreshBikeWith(bikeId)
                        }
                    }
                }

            }
        }
    }

    private fun refreshBikeWith(bikeId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            getBikeUseCase(bikeId).collectLatest { res ->
                when (res) {
                    is Resource.Error<*> -> {}
                    is Resource.Loading<*> -> {}
                    is Resource.Success<*> -> {
                        bikeWithConsumablesAndChecksDomain.update { res.data }
                    }
                }
            }

        }
    }

    fun onPhotoPicked(uri: Uri, bikeId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            addPictureToBikeUseCase(bikeId, uri).collectLatest { res ->
                when (res) {
                    is Resource.Error<*> -> {}
                    is Resource.Loading<*> -> {}
                    is Resource.Success<*> -> {
                        refreshBikeWith(bikeId)
                    }
                }
            }
        }
    }

    fun onAddInvoice(uri: Uri, bikeId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            addInvoiceUseCase(bikeId, uri).collectLatest { res ->
                when (res) {
                    is Resource.Error<*> -> {}
                    is Resource.Loading<*> -> {}
                    is Resource.Success<*> -> {
                        refreshBikeWith(bikeId)
                    }
                }
            }

        }
    }

    fun onDeleteInvoice(bikeId: Long, invoice: Invoice) {
        viewModelScope.launch(Dispatchers.Main) {
            deleteInvoiceUseCase(invoice).collectLatest { res ->
                when (res) {
                    is Resource.Error<*> -> {}
                    is Resource.Loading<*> -> {}
                    is Resource.Success<*> -> {
                        refreshBikeWith(bikeId)
                    }
                }
            }

        }
    }
}