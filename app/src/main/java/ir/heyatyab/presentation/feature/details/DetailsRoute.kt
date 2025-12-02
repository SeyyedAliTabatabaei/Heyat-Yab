package ir.heyatyab.presentation.feature.details

import android.Manifest
import android.content.Context
import android.graphics.drawable.Drawable
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import ir.heyatyab.R
import ir.heyatyab.domain.model.remote.Event
import ir.heyatyab.presentation.theme.LocalCustomColors
import okhttp3.OkHttpClient
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun DetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel() ,
    eventId : Long ,
    onBack: () -> Unit
) {

    val uiState = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect

    LaunchedEffect(Unit) {
        viewModel.processIntent(DetailsIntent.LoadEvent(eventId))
    }

    LaunchedEffect(Unit) {
        effect.collect {
            when(it){
                DetailsEffect.OnBack -> onBack()
            }
        }
    }


    DetailsScreen(
        modifier = modifier,
        state = uiState.value ,
        intent = viewModel::processIntent
    )

    
}

@Composable
private fun DetailsScreen(
    state : DetailsUiState,
    intent : (DetailsIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val colors = LocalCustomColors.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 24.dp)
    ) {
        Toolbar(
            title = state.event?.name ?: "-" ,
            onBack = { intent(DetailsIntent.OnBack) }
        )

        state.event?.let { event ->
            Content(
                state = state,
                intent = intent,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }

}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    state: DetailsUiState,
    intent: (DetailsIntent) -> Unit
) {
    val colors = LocalCustomColors.current
    val context = LocalContext.current
    val event = state.event ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        AsyncImage(
            model = event.posterUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop ,
            placeholder = painterResource(R.drawable.ic_image)
        )

        Spacer(Modifier.size(16.dp))

        InfoItem(
            label = stringResource(R.string.label_speaker),
            value = event.speaker
        )

        InfoItem(
            label = stringResource(R.string.label_maddah),
            value = event.maddah
        )

        InfoItem(
            label = stringResource(R.string.label_address),
            value = event.address
        )

        InfoItem(
            label = stringResource(R.string.label_start_time),
            value = event.datetimeStart
        )

        InfoItem(
            label = stringResource(R.string.label_end_time),
            value = event.datetimeEnd
        )

        InfoItem(
            label = stringResource(R.string.label_contact),
            value = event.contact
        )


        Spacer(Modifier.size(12.dp))

        if (!event.description.isNullOrBlank()) {
            Text(
                text = event.description ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(color = colors.textPrimary),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(Modifier.size(12.dp))

        if (event.location != null) {
            MapBox(
                lat = event.location!!.lat,
                lon = event.location!!.lng,
            )
        }
    }
}

@Composable
fun MapBox(
    lat: Double,
    lon: Double
) {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = {
            val map = MapView(context)
            map.setMultiTouchControls(true)

            val startPoint = GeoPoint(lat, lon)
            map.controller.setZoom(16.0)
            map.controller.setCenter(startPoint)

            val marker = Marker(map)
            marker.position = startPoint
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.icon = context.getDrawable(R.drawable.ic_location_pin_yellow)
            map.overlays.add(marker)

            map
        }
    )
}


@Composable
fun InfoItem(
    label: String,
    value: String?
) {
    val colors = LocalCustomColors.current
    if (value.isNullOrBlank()) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(color = colors.textSecondary),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(color = colors.textPrimary),
            modifier = Modifier.weight(2f)
        )
    }
}


@Composable
fun Toolbar(
    modifier: Modifier = Modifier ,
    title : String ,
    onBack : () -> Unit
) {
    val colors = LocalCustomColors.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp) ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right) ,
            contentDescription = "back" ,
            tint = colors.textPrimary ,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onBack()
                }
        )

        Text(
            textAlign = TextAlign.Center ,
            text = title ,
            style = MaterialTheme.typography.titleSmall.copy(color = colors.textPrimary) ,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

