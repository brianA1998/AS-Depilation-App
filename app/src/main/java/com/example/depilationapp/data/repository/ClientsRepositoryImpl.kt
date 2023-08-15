package com.example.depilationapp.data.repository

import com.example.depilationapp.core.mapToClient
import com.example.depilationapp.core.mapToZone
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
        val clientDocumentRef = clientsRef.document()

        if (client.id == "a123") {
            client.id = clientDocumentRef.id
        }
        clientDocumentRef.set(client.toMap())

        zones.forEach { zoneDepilate ->
            zoneDepilate.clientId = client.id // Actualizamos el clientId para cada zonaDepilate
            val zoneDocumentRef = if (zoneDepilate.id.isEmpty()) {
                zonesRef.document() // crea una referencia a un nuevo documento si la id de la zona está vacía
            } else {
                zonesRef.document(zoneDepilate.id) // usa la referencia al documento existente si la id de la zona no está vacía
            }

            zoneDepilate.id = zoneDocumentRef.id // asigna la id del documento a la zona
            zoneDocumentRef.set(zoneDepilate.toMap()) // guarda la zona en Firestore
        }
    }
}
