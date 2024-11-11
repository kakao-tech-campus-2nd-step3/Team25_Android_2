package com.kakaotech.team25M.data.network.services

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationUpdateLauncher(context: Context) {
    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val INTERVAL_MILLIS = 5000L

    private var _location: Location? = null
    private val location get() = _location

    @SuppressLint("MissingPermission")
    fun startLocationUpdate() {
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, INTERVAL_MILLIS)
                .build()

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
            .addOnFailureListener { fail ->
                Log.w("LocationService", "Failed Message: ${fail.localizedMessage}")
            }
    }

    fun stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun updateLocation(location: Location) {
        _location = location
    }

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            lastLocation?.let {
                updateLocation(it)
                Log.i(
                    "LocationService", "longitude: ${lastLocation.longitude}\n" + "" +
                        "latitude: ${lastLocation.latitude}"
                )
            }
        }
    }
}
