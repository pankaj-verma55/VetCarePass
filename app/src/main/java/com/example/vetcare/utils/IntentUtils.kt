package com.example.vetcare.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri

object IntentUtils {
    fun openDialer(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$phoneNumber".toUri()
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No phone app found", Toast.LENGTH_SHORT).show()
        }
    }

    fun openGoogleMapsDirections(context: Context, destination: String) {
        val encodedDest = Uri.encode(destination)
        val mapsUri = "google.navigation:q=$encodedDest".toUri()
        val mapIntent = Intent(Intent.ACTION_VIEW, mapsUri).apply {
            setPackage("com.google.android.apps.maps")
        }

        try {
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            val webUri = "https://www.google.com/maps/dir/?api=1&destination=$encodedDest".toUri()
            context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
        }
    }
}