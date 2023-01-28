package com.example.depilationapp.data.repository

import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.domain.repository.ClientsRepository
import com.example.depilationapp.domain.repository.ClientsResponse
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientsRepositoryImpl @Inject constructor(
    private val clientsRef: CollectionReference
) : ClientsRepository {


    override fun getClientsFromFirestore(): Flow<ClientsResponse> = callbackFlow {
        val snapshotListener = clientsRef.addSnapshotListener { snapshot, e ->
            val clientsResponse = if (snapshot != null) {
                val clients = snapshot.toObjects(Client::class.java)
                Response.Success(clients)
            } else {
                Response.Failure(e)
            }
            trySend(clientsResponse as ClientsResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }
}