package fr.zunkit.motorcyclemanager.domain.bikes

import android.net.Uri
import fr.zunkit.motorcyclemanager.core.FileManager
import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.models.Resource
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
            oldPath?.let { oldPath -> File(oldPath).takeIf { file -> file.exists() }?.delete() }
            val newPath = fileManager.copyImageToPersistentStorage(
                uri,
                "bike_${bikeId}_${System.currentTimeMillis()}.jpg"
            )


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