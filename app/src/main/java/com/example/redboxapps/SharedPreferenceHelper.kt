package com.example.redboxapps

import android.content.Context
import javax.inject.Inject

class SharedPreferenceHelper @Inject constructor(context: Context) {
    companion object {
        const val PREF_NAME = "geofence_preference"
        const val LOCATION_PERMISSION_STATE = "location_permission_state"
        const val GEOFENCE_STATE = "geofence_state"
    }

    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = preference.edit()

    fun setLocationPermissionState(state: Boolean) {
        editor.putBoolean(LOCATION_PERMISSION_STATE, state).apply()
    }

    fun getLocationPermissionState(): Boolean {
        return preference.getBoolean(LOCATION_PERMISSION_STATE, false)
    }

    fun setGeofenceState(state: Boolean) {
        editor.putBoolean(GEOFENCE_STATE, state).apply()
    }

    fun getGeofenceState(): Boolean {
        return preference.getBoolean(GEOFENCE_STATE, false)
    }
}