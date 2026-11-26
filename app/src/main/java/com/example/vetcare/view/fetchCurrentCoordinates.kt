package com.example.vetcare.view

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@SuppressLint("MissingPermission")
private suspend fun fetchCurrentCoordinates(context: Context): Pair<Double, Double>? {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        ?: return null

    // 1. Try cached location first
    val bestCached = locationManager.getProviders(true)
        .mapNotNull { provider -> runCatching { locationManager.getLastKnownLocation(provider) }.getOrNull() }
        .minByOrNull { it.accuracy }

    if (bestCached != null) {
        return bestCached.latitude to bestCached.longitude
    }

    // 2. Request a fresh location fix if cache is empty
    val provider = when {
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
        else -> return null
    }

    return suspendCancellableCoroutine { continuation ->
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationManager.removeUpdates(this)
                if (continuation.isActive) {
                    continuation.resume(location.latitude to location.longitude)
                }
            }
        }
        runCatching {
            locationManager.requestLocationUpdates(
                provider,
                0L,
                0f,
                listener,
                Looper.getMainLooper()
            )
        }.onFailure {
            if (continuation.isActive) continuation.resume(null)
        }
        continuation.invokeOnCancellation {
            locationManager.removeUpdates(listener)
        }
    }
}