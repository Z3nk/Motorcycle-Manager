package fr.zunkit.motorcyclemanager.presentation.bikedetails.models

import fr.zunkit.motorcyclemanager.domain.bikes.models.InvoiceDomain


data class Invoice(
    val id: Long,
    val fileName: String,
    val filePath: String,
    val date: String
) {
    constructor(entity: InvoiceDomain) : this(
        id = entity.id,
        fileName = entity.fileName,
        filePath = entity.filePath,
        date = entity.date
    )
}