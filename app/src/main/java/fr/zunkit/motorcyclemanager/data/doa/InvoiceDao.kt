package fr.zunkit.motorcyclemanager.data.doa

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fr.zunkit.motorcyclemanager.data.models.InvoiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {

    @Insert
    suspend fun insertInvoice(invoice: InvoiceEntity): Long

    @Insert
    suspend fun insertInvoices(invoices: List<InvoiceEntity>): List<Long>

    @Query("SELECT * FROM invoices WHERE bikeId = :bikeId")
    fun getInvoicesForBike(bikeId: Long): Flow<List<InvoiceEntity>>

    @Update
    suspend fun updateInvoice(invoice: InvoiceEntity): Int

    @Query("SELECT * FROM invoices WHERE id = :invoiceId")
    suspend fun getInvoiceById(invoiceId: Long): InvoiceEntity?

    @Delete
    suspend fun deleteInvoice(invoice: InvoiceEntity): Int

    @Query("DELETE FROM invoices WHERE id = :invoiceId")
    suspend fun deleteInvoiceById(invoiceId: Long): Int

    @Query("DELETE FROM invoices WHERE bikeId = :bikeId")
    suspend fun deleteInvoiceByBikeId(bikeId: Long)
}