package ir.heyatyab.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.heyatyab.R
import ir.heyatyab.presentation.theme.HeyatYabTheme
import ir.heyatyab.presentation.theme.LocalCustomColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SnackBarType{
    SUCCESS , ERROR
}

data class SnackBarData(
    val message: String?,
    val durationMillis: Long,
    val snackBarType: SnackBarType
)

class SnackBarController {
    var snackbarData :SnackBarData ?= null
    var showSnackbar = mutableStateOf<Boolean>(false)

    fun show(snackBarType: SnackBarType , message : String ?= null , durationMillis : Long = 3000) {
        snackbarData = SnackBarData(
            snackBarType = snackBarType ,
            message = message ,
            durationMillis = durationMillis
        )

        CoroutineScope(Dispatchers.Main).launch {
            showSnackbar.value = true
            delay(durationMillis)
            dismiss()
        }
    }

    fun dismiss() {
        showSnackbar.value = false
    }
}

@Composable
 fun SnackBarContent(
    modifier: Modifier = Modifier ,
    snackBarType: SnackBarType ,
    text : String ?= null
) {
    val colors = LocalCustomColors.current
    val backgroundColor = when(snackBarType){
        SnackBarType.SUCCESS -> colors.greenLight
        SnackBarType.ERROR -> colors.redLight
    }

    val borderColor = when(snackBarType){
        SnackBarType.SUCCESS -> colors.green
        SnackBarType.ERROR -> colors.red
    }

    val iconColor = when(snackBarType){
        SnackBarType.SUCCESS -> colors.green
        SnackBarType.ERROR -> colors.red
    }
    val icon = when(snackBarType){
        SnackBarType.SUCCESS -> painterResource(R.drawable.ic_check_fill)
        SnackBarType.ERROR -> painterResource(R.drawable.ic_close_fill)
    }

    val newText = when {
        text != null -> text
        snackBarType == SnackBarType.SUCCESS -> stringResource(R.string.operation_successful)
        snackBarType == SnackBarType.ERROR -> stringResource(R.string.operation_error)
        else -> ""
    }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(colors.background)
            .background(backgroundColor)
            .border(1.dp , borderColor , RoundedCornerShape(14.dp))
            .padding(horizontal = 10.dp , vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically ,
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Icon(
            painter = icon ,
            contentDescription = null ,
            tint = iconColor ,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = newText ,
            style = MaterialTheme.typography.bodySmall ,
            color = colors.textPrimary
        )
    }
}

@Preview
@Composable
private fun SnackBarContentPreview() {

    HeyatYabTheme {
        Column(
            modifier = Modifier.fillMaxWidth() ,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            SnackBarContent(snackBarType = SnackBarType.SUCCESS)

            SnackBarContent(snackBarType = SnackBarType.ERROR)
        }
    }

}