package fr.zunkit.motorcyclemanager.domain.invoices

import fr.zunkit.motorcyclemanager.data.repositories.invoices.InvoiceRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Invoice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteInvoiceUseCase @Inject constructor(private val invoiceRepository: InvoiceRepository) {
    operator fun invoke(invoice: Invoice): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                invoiceRepository.deleteInvoiceById(invoice.id)
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}