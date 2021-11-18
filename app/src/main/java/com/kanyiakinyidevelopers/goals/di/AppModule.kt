package com.kanyiakinyidevelopers.goals.di

import androidx.core.app.ActivityCompat
import com.kanyiakinyidevelopers.goals.data.AuthRepository
import com.kanyiakinyidevelopers.goals.data.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesAuthRepository() = AuthRepository()

    @Provides
    @Singleton
    fun providesMainRepository() = MainRepository()

}