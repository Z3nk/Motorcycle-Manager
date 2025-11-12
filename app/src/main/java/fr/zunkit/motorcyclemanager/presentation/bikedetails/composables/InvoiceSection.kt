package fr.zunkit.motorcyclemanager.presentation.bikedetails.composables

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import fr.zunkit.motorcyclemanager.R
import fr.zunkit.motorcyclemanager.presentation.bikedetails.composables.consumables.ConsumableItem
import fr.zunkit.motorcyclemanager.presentation.bikedetails.composables.consumables.EmptyConsumablesSection
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Invoice
import java.io.File
import kotlin.collections.forEach

@Composable
fun InvoiceSection(
    invoices: List<Invoice>,
    onDeleteInvoice: (Invoice) -> Unit,
    onAddInvoice: () -> Unit,
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.consommables),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = onAddInvoice) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_invoice),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (invoices.isEmpty()) {
                EmptyInvoiceSection()
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    invoices.forEach { invoice ->
                        InvoiceItem(
                            invoice = invoice,
                            onDeleteClick = { onDeleteInvoice(invoice) },
                            onClick = { openInvoice(context, invoice.filePath) })
                    }
                }
            }
        }
    }
}

private fun openInvoice(context: Context, filePath: String) {
    val file = File(filePath)
    if (!file.exists()) return

    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

    val mimeType = getMimeType(filePath) ?: "application/octet-stream"

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, mimeType)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, context.getString(R.string.no_app_for_read_file), Toast.LENGTH_SHORT).show()
    }
}

private fun getMimeType(filePath: String): String? {
    val extension = MimeTypeMap.getFileExtensionFromUrl(filePath)
    return if (extension != null) {
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.lowercase())
    } else null
}