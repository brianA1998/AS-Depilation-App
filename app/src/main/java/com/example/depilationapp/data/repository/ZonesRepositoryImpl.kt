package com.example.depilationapp.data.repository

import com.example.depilationapp.core.mapToZone
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.domain.repository.ZonesRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton
import com.example.depilationapp.core.toMap

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

    override fun getZonesFromFirestoreByClient(clientId: String) = callbackFlow {
        val subscription = zonesRef.whereEqualTo("clientId", clientId)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    trySend(Response.Failure(exception))
                    return@addSnapshotListener
                }

                val zones = snapshot?.documents?.map { document ->
                    mapToZone(document.data as Map<String, Any>)
                }?.toMutableList() ?: mutableListOf<ZoneDepilate>()

                trySend(Response.Success(zones))
            }

        awaitClose { subscription.remove() }
    }


    override suspend fun saveZone(zone: ZoneDepilate) {
        zonesRef.add(zone.toMap())
    }
}