package com.example.depilationapp.data.repository

import android.util.Log
import com.example.depilationapp.core.mapToClient
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.model.toMap
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
    private val clientsRef: CollectionReference,
    private val zonesRef: CollectionReference,
) : ClientsRepository {

    override fun getClientsFromFirestore() = callbackFlow {
        Log.i("MVVM","Estoy en getClientsFromFirestore de ClientsRepositoryImpl")
        val subscription = clientsRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(Failure(exception))
                return@addSnapshotListener
            }

            val clients = snapshot?.documents?.mapNotNull { document ->
                mapToClient(document.data as Map<String, Any>)
            }?.toMutableList() ?: mutableListOf()

            trySend(Success(clients))
        }

        awaitClose { subscription.remove() }
    }

    override suspend fun saveClient(client: Client, zones: List<ZoneDepilate>) {
        Log.d("id client", client.id)
        val clientDocumentRef = if (client.id.isEmpty()) {
            clientsRef.document()
        } else {
            clientsRef.document(client.id)
        }

        if (client.id.isEmpty()) {
            client.id =
                clientDocumentRef.id
        }

        clientDocumentRef.set(client.toMap())

        zones.forEach { zoneDepilate ->
            zoneDepilate.clientId = client.id
            val zoneDocumentRef = if (zoneDepilate.id.isEmpty()) {
                zonesRef.document()
            } else {
                zonesRef.document(zoneDepilate.id)
            }

            if (zoneDepilate.id.isEmpty()) {
                zoneDepilate.id =
                    zoneDocumentRef.id
            }

            zoneDocumentRef.set(zoneDepilate.toMap())
        }
    }
}