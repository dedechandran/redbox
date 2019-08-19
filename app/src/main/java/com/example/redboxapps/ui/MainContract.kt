package com.example.redboxapps.ui

import com.example.redboxapps.base.BaseContract
import com.google.android.gms.location.Geofence
import com.google.firebase.auth.FirebaseUser

interface MainContract{
    interface View : BaseContract.View{
        fun configureGeofence(geofences : List<Geofence>)
    }
    interface Presenter : BaseContract.Presenter<View>{
        fun loadRedboxCollection()
        fun getLocationPermissionState() : Boolean
        fun getGeofenceState() : Boolean
        fun setLocationPermissionState(state : Boolean)
        fun setGeofenceState(state : Boolean)
        fun loadDeviceToken()
    }
}