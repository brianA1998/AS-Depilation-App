package com.example.depilationapp.data.repository

import android.util.Log
import com.example.depilationapp.core.mapToZone
import com.example.depilationapp.data.model.ZoneDepilate
import com.example.depilationapp.data.model.toMap
import com.example.depilationapp.data.util.Response
import com.example.depilationapp.domain.repository.ZonesRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
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
        zonesRef.add(zone.toMap())
    }
}