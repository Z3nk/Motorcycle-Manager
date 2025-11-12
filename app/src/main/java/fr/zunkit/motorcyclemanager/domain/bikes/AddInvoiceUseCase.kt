package fr.zunkit.motorcyclemanager.domain.bikes

import android.net.Uri
import fr.zunkit.motorcyclemanager.core.FileManager
import fr.zunkit.motorcyclemanager.data.models.InvoiceEntity
import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.data.repositories.invoices.InvoiceRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Invoice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

class AddInvoiceUseCase @Inject constructor(
    private val invoiceRepository: InvoiceRepository,
    private val fileManager: FileManager
) {
    operator fun invoke(bikeId: Long, uri: Uri): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {
            val extension = fileManager.getFileExtension(uri) ?: "jpg"
            val fileName = "invoice_${bikeId}_${System.currentTimeMillis()}.${extension}"
            val newPath = fileManager.copyImageToPersistentStorage(
                uri,
                fileName
            )


            invoiceRepository.createInvoice(
                InvoiceEntity(
                    bikeId = bikeId,
                    fileName = fileManager.getFileName(uri) ?: fileName,
                    filePath = newPath,
                    date = getTodayAutoFormatted()
                )
            )

            emit(Resource.Success(Unit))
        } catch (e: FileNotFoundException) {
            emit(Resource.Error(e))
        } catch (e: IOException) {
            emit(Resource.Error(e))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    private fun getTodayAutoFormatted(): String {
        val locale = Locale.getDefault()
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            .withLocale(locale)
        return LocalDateTime.now().format(formatter)
    }
}