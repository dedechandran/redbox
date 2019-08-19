package com.example.redboxapps.ui

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.redboxapps.*
import com.example.redboxapps.base.BaseActivity
import com.example.redboxapps.ui.backgroundtask.GeofenceBroadcastReceiver
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    lateinit var geofencingClient: GeofencingClient
    private lateinit var homeGeofence: Geofence
    //    private lateinit var ariqKostGeofence: Geofence
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(this, BROADCAST_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    @Inject
    lateinit var presenter: MainContract.Presenter
    override fun configureGeofence(geofences: List<Geofence>) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onAttach(this)
        presenter.loadRedboxCollection()
        if (intent.extras != null) {
            for (key in intent.extras!!.keySet()) {
                val value = intent.extras!!.get(key)
                Log.d("MAINACTIVITY", "Key: $key Value: $value")
            }
        }
        homeGeofence = Geofence.Builder()
            .setRequestId("322")
            .setCircularRegion(
                -6.8991132,
                107.6558128,
                100F
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()
//        ariqKostGeofence = Geofence.Builder()
//            .setRequestId(GEOFENCE_ARIQ_KOST_REQUEST_ID)
//            .setCircularRegion(
//                GEOFENCE_ARIQ_KOST_LATITUDE,
//                GEOFENCE_ARIQ_KOST_LONGITUDE,
//                GEOFENCE_RADIUS
//            )
//            .setExpirationDuration(Geofence.NEVER_EXPIRE)
//            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
//            .build()
    }

    override fun onResume() {
        super.onResume()
        if (!presenter.getLocationPermissionState()) {
            if (!checkPermission()) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_LOCATION_REQUEST_CODE
                )
            }
        } else {
            if (!presenter.getGeofenceState()) {
                geofencingClient.addGeofences(getGeofencingRequest(), geofencePendingIntent).run {
                    addOnSuccessListener {
                        Log.d("Geofence", "Berhasil ditambahkan")
                        presenter.setGeofenceState(true)
                    }
                    addOnFailureListener {
                        Log.d("Geofence", "Gagal ditambahkan")
                    }
                }
            } else {
                Toast.makeText(this, "Geofence Telah Terdaftar Sebelumnya", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getGeofencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
            addGeofence(homeGeofence)
        }.build()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            11 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    presenter.setLocationPermissionState(true)
                    presenter.loadDeviceToken()
                } else {
                    finish()
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        var permission = false
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
            permission = true
        }
        return permission
    }
}
