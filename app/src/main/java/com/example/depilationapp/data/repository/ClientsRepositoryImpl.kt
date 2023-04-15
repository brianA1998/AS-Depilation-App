package com.example.depilationapp.data.repository

import com.example.depilationapp.core.mapToClient
import com.example.depilationapp.core.toMap
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.util.Response.Failure
import com.example.depilationapp.data.util.Response.Success
import com.example.depilationapp.domain.repository.ClientsRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientsRepositoryImpl @Inject constructor(
    private val clientsRef: CollectionReference
) : ClientsRepository {


    override fun getClientsFromFirestore() = callbackFlow {
        val subscription = clientsRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(Failure(exception))
                return@addSnapshotListener
            }

            val clients = snapshot?.documents?.map { document ->
                mapToClient(document.data as Map<String, Any>)
            }?.toMutableList() ?: mutableListOf()

            trySend(Success(clients))
        }

        awaitClose { subscription.remove() }
    }

    override suspend fun saveClient(client: Client) {
        clientsRef.add(client.toMap())
    }
}