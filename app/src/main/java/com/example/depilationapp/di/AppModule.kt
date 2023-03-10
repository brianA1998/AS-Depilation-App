package com.example.depilationapp.di

import com.example.depilationapp.core.Constants
import com.example.depilationapp.data.repository.ClientsRepositoryImpl
import com.example.depilationapp.domain.repository.ClientsRepository
import com.example.depilationapp.domain.use_case.GetClients
import com.example.depilationapp.domain.use_case.UseCases
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideClientsRef() = Firebase.firestore.collection(Constants.CLIENTS)

    @Provides
    fun provideClientsRepository(clientsRef: CollectionReference): ClientsRepository =
        ClientsRepositoryImpl(clientsRef)

    @Provides
    fun provideUseCases(repo: ClientsRepository) = UseCases(getClients = GetClients(repo))
}