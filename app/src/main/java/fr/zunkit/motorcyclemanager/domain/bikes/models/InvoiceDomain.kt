package fr.zunkit.motorcyclemanager.domain.bikes.models


data class InvoiceDomain(
    val id: Long,
    val fileName: String,
    val filePath: String,
    val date: String
)