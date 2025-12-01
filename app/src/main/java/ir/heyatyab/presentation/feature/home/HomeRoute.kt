package ir.heyatyab.presentation.feature.home

import android.Manifest
import android.content.Context
import android.graphics.drawable.Drawable
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import ir.heyatyab.R
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.presentation.theme.LocalCustomColors
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel() ,
    navigateToEventDetails : (id : Long) -> Unit
) {

    val uiState = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect

    LaunchedEffect(Unit) {
        viewModel.processIntent(HomeIntent.LoadEvents)
    }



    LaunchedEffect(Unit) {
        effect.collect {
            when(it) {
                is HomeEffect.ShowEventDetails -> {
                    it.event.id?.let { id ->
                        navigateToEventDetails(id)
                    }
                }
            }
        }
    }


    HomeScreen(
        modifier = modifier,
        state = uiState.value ,
        intent = viewModel::processIntent
    )

    
}

@Composable
private fun HomeScreen(
    state : HomeUiState,
    intent : (HomeIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val colors = LocalCustomColors.current

    var lat by remember { mutableStateOf<Double?>(null) }
    var long by remember { mutableStateOf<Double?>(null) }

    LocationPermission {
        getUserLocation(context) { la, lo ->
            lat = la
            long = lo
        }
    }

    Box(
        modifier = modifier.fillMaxSize() ,
        contentAlignment = Alignment.BottomStart
    ) {
        OSMMap(
            lat = lat ?: 0.0 ,
            lon = long ?: 0.0 ,
            list = state.events ,
            onClickEvent = {
                intent(HomeIntent.ShowEventDetails(it))
            }
        )

        Icon(
            painter = painterResource(R.drawable.ic_location_define) ,
            contentDescription = "get current location" ,
            tint = colors.primary ,
            modifier = Modifier
                .padding(16.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(colors.background)
                .padding(10.dp)
                .clickable {
                    getUserLocation(context) { la, lo ->
                        lat = la
                        long = lo
                    }
                }
        )

    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermission(onGranted: () -> Unit) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    when {
        permissionState.allPermissionsGranted -> onGranted()
        else -> Text(stringResource(R.string.location_permission))
    }
}

@Composable
fun OSMMap(lat: Double, lon: Double , list : List<Event.Response.Data> , onClickEvent : (Event.Response.Data) -> Unit) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            MapView(it).apply {
                org.osmdroid.config.Configuration.getInstance().load(it, it.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
                setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)

                controller.setZoom(16.0)
                controller.setCenter(GeoPoint(lat, lon))

                setMultiTouchControls(true)

                overlays.add(marker(
                    lat = lat ,
                    lon = lon ,
                    mapView = this ,
                    icon = context.getDrawable(R.drawable.ic_location_pin_green) ,
                    onClick = {}
                ))
            }
        },
        update = { mapView ->
            mapView.overlays.removeAll { it is Marker }

            mapView.overlays.add(
                marker(
                    lat = lat,
                    lon = lon,
                    mapView = mapView,
                    icon = context.getDrawable(R.drawable.ic_location_pin_green) ,
                    onClick = {}
                )
            )

            list.forEach { event ->
                event.location?.let { loc ->
                    mapView.overlays.add(
                        marker(
                            lat = loc.lat,
                            lon = loc.lng,
                            mapView = mapView,
                            icon = context.getDrawable(R.drawable.ic_location_pin_yellow) ,
                            onClick = {
                                onClickEvent(event)
                            }
                        )
                    )
                }
            }

            mapView.controller.animateTo(GeoPoint(lat, lon))
        }
    )
}

fun marker(lat: Double , lon: Double , mapView: MapView , icon : Drawable? , onClick : () -> Unit) : Marker {
    val marker = Marker(mapView)
    marker.position = GeoPoint(lat, lon)
    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
    marker.icon = icon
    marker.setOnMarkerClickListener { marker, mapView ->
        onClick()
        return@setOnMarkerClickListener true
    }
    return marker
}

fun getUserLocation(context: Context, onLocation: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    try {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onLocation(location.latitude, location.longitude)
                }
            }
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}



