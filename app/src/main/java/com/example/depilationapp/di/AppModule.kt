package com.example.depilationapp.di

import com.example.depilationapp.core.Constants
import com.example.depilationapp.data.repository.ClientsRepositoryImpl
import com.example.depilationapp.data.repository.ZonesRepositoryImpl
import com.example.depilationapp.domain.repository.ClientsRepository
import com.example.depilationapp.domain.repository.ZonesRepository
import com.example.depilationapp.domain.use_case.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ClientsCollection

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ZonesCollection

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @ClientsCollection
    @Provides
    fun provideClientsRef() = Firebase.firestore.collection(Constants.CLIENTS)

    @ZonesCollection
    @Provides
    fun provideZonesRef() = Firebase.firestore.collection(Constants.ZONES)

    @Provides
    fun provideClientsRepository(
        @ClientsCollection clientsRef: CollectionReference,
        @ZonesCollection zonesRef: CollectionReference
    ): ClientsRepository =
        ClientsRepositoryImpl(clientsRef, zonesRef)

    @Provides
    fun provideZonesRepository(@ZonesCollection zonesRef: CollectionReference): ZonesRepository =
        ZonesRepositoryImpl(zonesRef)

    @Provides
    fun provideUseCases(clientsRepo: ClientsRepository, zonesRepo: ZonesRepository) =
        UseCases(
            getClients = GetClients(clientsRepo),
            saveClient = SaveClient(clientsRepo),
            getZones = GetZones(zonesRepo),
            saveZone = SaveZone(zonesRepo)
        )
}
