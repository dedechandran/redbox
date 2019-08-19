package com.example.redboxapps.ui

import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.redboxapps.SharedPreferenceHelper
import com.example.redboxapps.data.RedboxRepository
import com.example.redboxapps.domain.Redbox
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class MainPresenter @Inject constructor() : MainContract.Presenter {


    @Inject
    lateinit var auth : FirebaseAuth

    override fun loadDeviceToken() {
        redboxRepository.loadDeviceToken()
    }

    override fun getGeofenceState() = sharedPreferenceHelper.getGeofenceState()

    override fun setLocationPermissionState(state: Boolean) {
        sharedPreferenceHelper.setLocationPermissionState(state)
    }

    override fun setGeofenceState(state: Boolean) {
        sharedPreferenceHelper.setGeofenceState(state)
    }

    override fun getLocationPermissionState() = sharedPreferenceHelper.getLocationPermissionState()

    override fun loadRedboxCollection(){
        redboxRepository.loadRedboxCollection(object  : RedboxRepository.RepositoryCallback<List<Redbox>>{
            override fun onSucces(data: List<Redbox>) {
                Log.d("MAINACTIVITY",data.toString())
            }

            override fun onError(e: Throwable) {

            }

        })
    }



    @Inject
    lateinit var redboxRepository: RedboxRepository
    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    private lateinit var mainView: MainContract.View
    override fun onAttach(view: MainContract.View) {
        mainView = view
    }

    override fun onDetach() {

    }
}