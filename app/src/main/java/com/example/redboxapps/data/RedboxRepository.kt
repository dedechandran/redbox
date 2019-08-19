package com.example.redboxapps.data

import android.util.Log
import com.example.redboxapps.REDBOX_COLLECTION
import com.example.redboxapps.domain.Redbox
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class RedboxRepository @Inject constructor() {
    @Inject
    lateinit var firestore: FirebaseFirestore
    @Inject
    lateinit var fcm: FirebaseMessaging
    @Inject
    lateinit var instanceId: FirebaseInstanceId

    fun loadRedboxCollection(callback: RepositoryCallback<List<Redbox>>) {
        val data = mutableListOf<Redbox>()
        firestore.collection("redbox").addSnapshotListener { docs, e ->
            docs?.documentChanges?.forEach { dc ->
                if (dc.type == DocumentChange.Type.ADDED) {
                    val location: GeoPoint? = dc.document.getGeoPoint("location")
                    data.add(Redbox(location))
                }
            }
            callback.onSucces(data)
        }
    }

    fun loadDeviceToken() {
        instanceId.instanceId.addOnSuccessListener {
            val data = hashMapOf("token" to it.token)
            firestore.collection("users").add(data)
        }
    }

    fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(REDBOX_COLLECTION)
    }

    fun unsubscribeTopic() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(REDBOX_COLLECTION)
    }


    interface RepositoryCallback<T> {
        fun onSucces(data: T)
        fun onError(e: Throwable)
    }

}