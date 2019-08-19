package com.example.redboxapps.domain

import com.google.firebase.firestore.GeoPoint

data class Redbox(
    val location : GeoPoint?
)