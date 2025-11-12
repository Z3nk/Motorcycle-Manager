package fr.zunkit.motorcyclemanager.data.repositories.invoices

import fr.zunkit.motorcyclemanager.data.doa.InvoiceDao
import fr.zunkit.motorcyclemanager.data.models.InvoiceEntity
import javax.inject.Inject

class InvoiceRepository @Inject constructor(
    private val invoiceDao: InvoiceDao
) {
    suspend fun getInvoiceById(
        invoiceId: Long
    ): InvoiceEntity? {
        return invoiceDao.getInvoiceById(invoiceId)
    }

    suspend fun createInvoice(
        invoice: InvoiceEntity
    ) {
        invoiceDao.insertInvoice(invoice)
    }

    suspend fun updateInvoice(invoiceEntity: InvoiceEntity) {
        invoiceDao.updateInvoice(invoiceEntity)
    }

    suspend fun deleteInvoiceById(invoiceId: Long) {
        invoiceDao.deleteInvoiceById(invoiceId)
    }

}