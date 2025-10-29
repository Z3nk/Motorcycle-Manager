package fr.zunkit.motorcyclemanager.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.zunkit.motorcyclemanager.core.FileManager
import fr.zunkit.motorcyclemanager.core.FileManagerImpl

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideFileManager(
        @ApplicationContext context: Context
    ): FileManager = FileManagerImpl(context)
}