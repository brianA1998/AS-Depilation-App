package com.example.depilationapp.data.repository

import android.util.Log
import com.example.depilationapp.core.mapToZone
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.model.toMap
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.domain.repository.ZonesRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ZonesRepositoryImpl @Inject constructor(
    private val zonesRef: CollectionReference
) : ZonesRepository {


    override fun getZonesFromFirestore() = callbackFlow {
        val subscription = zonesRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                trySend(Response.Failure(exception))
                return@addSnapshotListener
            }

            val zones = snapshot?.documents?.map { document ->
                mapToZone(document.data as Map<String, Any>)
            }?.toMutableList() ?: mutableListOf<ZoneDepilate>()

            trySend(Response.Success<List<ZoneDepilate>>(zones))
        }

        awaitClose { subscription.remove() }
    }

    override fun getZonesFromFirestoreByClient(clientId: String) = flow {

        val snapshot = zonesRef.whereEqualTo("clientId", clientId).get().await()
        val zones = snapshot.documents.map { document ->
            mapToZone(document.data as Map<String, Any>)
        }
        Log.d("ZonesRepository", "Fetched zones: $zones")
        emit(Response.Success(zones))
    }


    override suspend fun saveZone(zone: ZoneDepilate) {
        val zoneDocumentRef = if (zone.id.isEmpty()) {
            zonesRef.document() // Crea una nueva ID de documento si la zona es nueva
        } else {
            zonesRef.document(zone.id) // Usa la ID de documento existente si la zona ya existe
        }

        zoneDocumentRef.set(zone.toMap()) // Guarda la zona en Firestore
    }

    override suspend fun updateZoneIntensity(zoneId: String, intensity: Int) {
        try {
            Log.d("ZonesRepository", "Zones: $zoneId");
            Log.d("ZonesRepository", "intensity: $intensity");
            val zoneDocumentRef = zonesRef.document(zoneId)
            val zoneSnapshot = zoneDocumentRef.get().await()
            if (zoneSnapshot.exists()) {
                val updatedZone = zoneSnapshot.toObject(ZoneDepilate::class.java)
                updatedZone?.intense = intensity // Actualiza la intensidad
                Log.d("ZonesRepository", "updatedZone: $updatedZone")
                zoneDocumentRef.set(updatedZone!!).await() // Guarda la zona actualizada en Firestore
                Log.d("ZonesRepository", "Zone updated successfully")
            } else {
                throw IllegalArgumentException("La zona con el ID $zoneId no existe")
            }
        } catch (e: Exception) {
            // Si ocurre un error, puedes manejarlo lanzando una excepción
            throw Exception("Failed to update zone intensity", e)
        }
    }

}