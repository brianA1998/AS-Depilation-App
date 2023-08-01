package com.example.depilationapp.data.repository

import com.example.depilationapp.core.mapToClient
import com.example.depilationapp.core.mapToZone
import com.example.depilationapp.data.model.Client
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
        val subscription = clientsRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(Failure(exception))
                return@addSnapshotListener
            }

            val clients = snapshot?.documents?.mapNotNull { document ->
                val client = mapToClient(document.data as Map<String, Any>)
                val zoneDepilateSubscription = zonesRef
                    .whereEqualTo("clientId", client.id)
                    .get()
                    .addOnSuccessListener { zonesSnapshot ->
                        client.zoneDepilate = zonesSnapshot.documents.mapNotNull { zoneDocument ->
                            mapToZone(zoneDocument.data as Map<String, Any>)
                        }
                    }
                client
            }?.toMutableList() ?: mutableListOf()

            trySend(Success(clients))
        }

        awaitClose { subscription.remove() }
    }

    override suspend fun saveClient(client: Client) {

        val clientDocumentRef = clientsRef.document()

        if (client.id == "a123") {
            client.id = clientDocumentRef.id
        }
        clientDocumentRef.set(client.toMap())


        client.zoneDepilate.forEach { zoneDepilate ->
            zoneDepilate.clientId = client.id // Actualizamos el clientId para cada zonaDepilate
            val zoneDocumentRef = zonesRef.document() // crea una referencia a un nuevo documento

            if (zoneDepilate.id.isEmpty()) {
                zoneDepilate.id = zoneDocumentRef.id // asigna la id del documento a la zona
            }

            zoneDocumentRef.set(zoneDepilate.toMap()) // guarda la zona en Firestore
        }
    }
}