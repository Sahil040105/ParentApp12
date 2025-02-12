package com.example.parentapp.services

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.firebase.database.FirebaseDatabase

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Define location request parameters
        locationRequest = LocationRequest.create().apply {
            interval = 5000 // Request location updates every 5 seconds
            fastestInterval = 2000 // Fastest update interval
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    sendLocationToFirebase(location.latitude, location.longitude)
                }
            }
        }

        startTracking()
    }

    private fun startTracking() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun sendLocationToFirebase(latitude: Double, longitude: Double) {
        val childId = "uniqueChildId" // TODO: Replace with actual child ID
        val database = FirebaseDatabase.getInstance().reference
            .child("child_locations")
            .child(childId)

        val locationData = mapOf(
            "latitude" to latitude,
            "longitude" to longitude
        )
        database.setValue(locationData)
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
