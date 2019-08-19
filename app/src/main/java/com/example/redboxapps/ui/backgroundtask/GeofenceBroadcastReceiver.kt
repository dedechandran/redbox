package com.example.redboxapps.ui.backgroundtask


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.redboxapps.data.RedboxRepository
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import dagger.android.AndroidInjection
import javax.inject.Inject

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repository : RedboxRepository
    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this,context)
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            Log.d("Geofence", "ERROR")
            return
        }
        val geofenceTransition = geofencingEvent.geofenceTransition
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            val triggerGeofence = geofencingEvent.triggeringGeofences
            repository.subscribeTopic()

            Log.d("Geofence", triggerGeofence[0].toString())
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            repository.unsubscribeTopic()
            Log.d("Geofence", "ERROR")
        }
    }


}