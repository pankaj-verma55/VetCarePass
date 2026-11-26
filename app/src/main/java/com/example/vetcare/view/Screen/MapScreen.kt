package com.example.vetcare.view.Screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Position
import java.util.Locale
import kotlin.coroutines.resume

private val MapTeal = Color(0xFF24A8C2)
private val MapNavy = Color(0xFF213A63)
private val MapSubGray = Color(0xFF7B8794)
private val MapBorder = Color(0xFFE6EAF0)

private data class VetPin(
    val vet: VetDoctor,
    val latitude: Double,
    val longitude: Double,
    val distanceKm: Float
)

@Composable
fun MapScreen(
    vetsList: List<VetDoctor> = vets,
    modifier: Modifier = Modifier,
    onDoctorClick: (VetDoctor) -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Default reference center (Sirsa) if GPS permission is denied
    val fallbackLat = 29.5321
    val fallbackLng = 75.0318

    var userLat by remember { mutableDoubleStateOf(fallbackLat) }
    var userLng by remember { mutableDoubleStateOf(fallbackLng) }
    var hasLiveLocation by remember { mutableStateOf(false) }

    // Filter state: null = All, 5f = 5 km, 10f = 10 km, 25f = 25 km
    var selectedRadiusKm by remember { mutableStateOf<Float?>(null) }
    var showFilterMenu by remember { mutableStateOf(false) }

    var allVetPins by remember { mutableStateOf<List<VetPin>>(emptyList()) }

    val cameraState = rememberCameraState(
        firstPosition = CameraPosition(
            target = Position(longitude = userLng, latitude = userLat),
            zoom = 14.0
        )
    )

    // Helper to fetch live GPS and center the map on the user's blue dot
    suspend fun updateAndCenterUserLocation() {
        fetchCurrentCoordinates(context)?.let { (lat, lng) ->
            userLat = lat
            userLng = lng
            hasLiveLocation = true
            cameraState.animateTo(
                CameraPosition(
                    target = Position(longitude = lng, latitude = lat),
                    zoom = 15.0
                )
            )
        }
    }

    // Request fine/coarse location permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            scope.launch { updateAndCenterUserLocation() }
        }
    }

    LaunchedEffect(Unit) {
        val hasFine = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFine || hasCoarse) {
            updateAndCenterUserLocation()
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    // Geocode hospital addresses and calculate real distance in KM from user location
    LaunchedEffect(vetsList, userLat, userLng) {
        val resolved = withContext(Dispatchers.IO) {
            val geocoder = Geocoder(context, Locale.getDefault())
            vetsList.mapIndexed { index, vet ->
                @Suppress("DEPRECATION")
                val loc = runCatching {
                    geocoder.getFromLocationName(vet.direction, 1)?.firstOrNull()
                }.getOrNull()

                val offset = index * 0.0012
                val clinicLat = (loc?.latitude ?: userLat) + offset
                val clinicLng = (loc?.longitude ?: userLng) + offset

                val distanceResults = FloatArray(1)
                Location.distanceBetween(userLat, userLng, clinicLat, clinicLng, distanceResults)
                val km = distanceResults[0] / 1000f

                VetPin(
                    vet = vet,
                    latitude = clinicLat,
                    longitude = clinicLng,
                    distanceKm = km
                )
            }.sortedBy { it.distanceKm }
        }
        allVetPins = resolved
    }

    // Apply selected distance filter (e.g. 10 km)
    val filteredPins = remember(allVetPins, selectedRadiusKm) {
        val maxKm = selectedRadiusKm
        if (maxKm == null) allVetPins else allVetPins.filter { it.distanceKm <= maxKm }
    }

    var selectedPin by remember { mutableStateOf<VetPin?>(null) }

    LaunchedEffect(filteredPins) {
        if (selectedPin == null || filteredPins.none { it.vet == selectedPin?.vet }) {
            selectedPin = filteredPins.firstOrNull()
        }
    }

    // GeoJSON for user's current GPS dot
    val userGeoJsonString = remember(userLat, userLng) {
        """
        {
          "type": "FeatureCollection",
          "features": [{
            "type": "Feature",
            "geometry": { "type": "Point", "coordinates": [$userLng, $userLat] },
            "properties": {}
          }]
        }
        """.trimIndent()
    }

    // GeoJSON for veterinary clinics
    val clinicsGeoJsonString = remember(filteredPins) {
        val featuresJson = filteredPins.joinToString(",") { pin ->
            """
            {
              "type": "Feature",
              "geometry": {
                "type": "Point",
                "coordinates": [${pin.longitude}, ${pin.latitude}]
              },
              "properties": {}
            }
            """.trimIndent()
        }
        """{"type":"FeatureCollection","features":[$featuresJson]}"""
    }

    Box(modifier = modifier.fillMaxSize()) {
        // 1. Interactive Free Map
        MaplibreMap(
            modifier = Modifier.fillMaxSize(),
            baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
            cameraState = cameraState
        ) {
            // A. User Current Location Marker (Blue Halo + Solid Blue Dot)
            val userSource = rememberGeoJsonSource(
                data = GeoJsonData.JsonString(userGeoJsonString)
            )
            CircleLayer(
                id = "user-location-halo",
                source = userSource,
                radius = const(22.dp),
                color = const(Color(0x331976D2))
            )
            CircleLayer(
                id = "user-location-dot",
                source = userSource,
                radius = const(8.dp),
                color = const(Color(0xFF1976D2)),
                strokeWidth = const(3.dp),
                strokeColor = const(Color.White)
            )

            // B. Veterinary Clinic Pins
            val vetSource = rememberGeoJsonSource(
                data = GeoJsonData.JsonString(clinicsGeoJsonString)
            )
            CircleLayer(
                id = "vet-clinic-pins",
                source = vetSource,
                radius = const(10.dp),
                color = const(MapTeal),
                strokeWidth = const(3.dp),
                strokeColor = const(Color.White)
            )
        }

        // 2. Top Bar with Hospital Count + Top-Right Radius Filter
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 12.dp)
        ) {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(start = 14.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MapTeal,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Hospitals (${filteredPins.size})",
                        color = MapNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f)
                    )

                    // Top-Right Distance Filter Button + Dropdown
                    Box {
                        Surface(
                            modifier = Modifier.clickable { showFilterMenu = true },
                            shape = RoundedCornerShape(10.dp),
                            color = if (selectedRadiusKm != null) MapTeal else Color(0xFFE3F6FB)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Filter Radius",
                                    tint = if (selectedRadiusKm != null) Color.White else MapTeal,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = selectedRadiusKm?.let { "${it.toInt()} km" } ?: "All",
                                    color = if (selectedRadiusKm != null) Color.White else MapTeal,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = showFilterMenu,
                            onDismissRequest = { showFilterMenu = false },
                            containerColor = Color.White
                        ) {
                            val filterOptions = listOf(
                                null to "All Locations",
                                5f to "Within 5 km",
                                10f to "Within 10 km",
                                25f to "Within 25 km"
                            )
                            filterOptions.forEach { (radius, label) ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = label,
                                            color = if (selectedRadiusKm == radius) MapTeal else MapNavy,
                                            fontWeight = if (selectedRadiusKm == radius) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    onClick = {
                                        selectedRadiusKm = radius
                                        showFilterMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Horizontal Hospital Selector Chips (Tapping a chip animates camera to that clinic)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredPins) { pin ->
                    val isSelected = selectedPin?.vet == pin.vet
                    Surface(
                        modifier = Modifier.clickable {
                            selectedPin = pin
                            scope.launch {
                                cameraState.animateTo(
                                    CameraPosition(
                                        target = Position(longitude = pin.longitude, latitude = pin.latitude),
                                        zoom = 15.0
                                    )
                                )
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) MapNavy else Color.White,
                        border = BorderStroke(1.dp, if (isSelected) MapNavy else MapBorder),
                        shadowElevation = 2.dp
                    ) {
                        Text(
                            text = "${pin.vet.name} (${String.format(Locale.US, "%.1f", pin.distanceKm)} km)",
                            color = if (isSelected) Color.White else MapNavy,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // 3. Recenter on My Current Location Button
        SmallFloatingActionButton(
            onClick = {
                scope.launch {
                    updateAndCenterUserLocation()
                    if (!hasLiveLocation) {
                        cameraState.animateTo(
                            CameraPosition(
                                target = Position(longitude = userLng, latitude = userLat),
                                zoom = 15.0
                            )
                        )
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp),
            containerColor = Color.White,
            contentColor = Color(0xFF1976D2),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "My Current Location"
            )
        }

        // 4. Bottom Selected Hospital Card (or Empty State if none within radius)
        if (filteredPins.isEmpty() && allVetPins.isNotEmpty()) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = BorderStroke(1.dp, MapBorder),
                shadowElevation = 6.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "No veterinary hospitals found within ${selectedRadiusKm?.toInt() ?: 10} km.",
                        color = MapNavy,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { selectedRadiusKm = null }) {
                        Text("Show All", color = MapTeal, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            selectedPin?.let { pin ->
                val vet = pin.vet

                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .clickable { onDoctorClick(vet) },
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, MapBorder),
                    shadowElevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = vet.imageRes),
                            contentDescription = vet.name,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(vet.avatarBg),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = vet.name,
                                color = MapNavy,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = vet.direction,
                                color = MapSubGray,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFF6C23E),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = vet.rating,
                                    color = Color(0xFFF6C23E),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = MapNavy,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "${String.format(Locale.US, "%.1f", pin.distanceKm)} KM Away",
                                    color = MapSubGray,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(
                                onClick = { openDialer(context, vet.phone) },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color(0xFFE3F6FB), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "Call",
                                    tint = MapTeal,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            IconButton(
                                onClick = { openGoogleMapsDirections(context, vet.direction) },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color(0xFFE8EEF5), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Directions,
                                    contentDescription = "Directions",
                                    tint = MapNavy,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

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