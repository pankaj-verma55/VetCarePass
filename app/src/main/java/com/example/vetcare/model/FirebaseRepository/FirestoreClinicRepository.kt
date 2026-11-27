package com.example.vetcare.model.FirebaseRepository

import com.example.vetcare.model.data.FirestoreClinicItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreClinicRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val clinicsCollection = db.collection("clinics")

    // Real-time stream of clinics from Firestore (No Auth required)
    fun observeClinics(): Flow<List<FirestoreClinicItem>> = callbackFlow {
        val registration = clinicsCollection.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) {
                trySend(emptyList())
                return@addSnapshotListener
            }

            val clinics = snapshot.documents.mapNotNull { doc ->
                doc.toObject(FirestoreClinicItem::class.java)?.copy(id = doc.id)
            }
            trySend(clinics)
        }

        awaitClose { registration.remove() }
    }

    // Save a clinic / place reference to Firestore (No Auth required)
    fun saveClinic(
        clinic: FirestoreClinicItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val docRef = if (clinic.placeId.isNotBlank()) {
            // Use placeId as document ID to prevent duplicate entries
            clinicsCollection.document(clinic.placeId)
        } else {
            clinicsCollection.document()
        }

        val finalClinic = clinic.copy(
            id = docRef.id,
            createdAt = System.currentTimeMillis()
        )

        docRef.set(finalClinic)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                onError(e.localizedMessage ?: "Failed to save clinic")
            }
    }
}