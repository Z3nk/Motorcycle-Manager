package fr.zunkit.motorcyclemanager.domain.bikes

import android.content.Context
import android.net.Uri
import androidx.camera.core.UseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.zunkit.motorcyclemanager.core.FileManager
import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity
import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addhour.models.AddHour
import fr.zunkit.motorcyclemanager.presentation.bikedetails.extensions.copyImageToPersistentStorage
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Consumable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

class AddPictureToBikeUseCase @Inject constructor(
    private val bikeRepository: BikeRepository,
    private val fileManager: FileManager
) {
    operator fun invoke(bikeId: Long, uri: Uri): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {
            val oldPath = bikeRepository.getCurrentPhotoPath(bikeId)
            val newPath = fileManager.copyImageToPersistentStorage(uri, bikeId)

            oldPath?.let { oldPath -> File(oldPath).takeIf { file -> file.exists() }?.delete() }

            bikeRepository.addPictureToBike(bikeId, newPath)

            emit(Resource.Success(Unit))
        } catch (e: FileNotFoundException) {
            emit(Resource.Error(e))
        } catch (e: IOException) {
            emit(Resource.Error(e))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}