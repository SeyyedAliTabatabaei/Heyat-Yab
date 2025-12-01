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
fun DetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel() ,
    eventId : Long
) {

    val uiState = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect

    LaunchedEffect(Unit) {
        viewModel.processIntent(DetailsIntent.LoadEvent(eventId))
    }

    LaunchedEffect(Unit) {
        effect.collect {

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



}

@Composable
fun Toolbar(modifier: Modifier = Modifier) {

}

