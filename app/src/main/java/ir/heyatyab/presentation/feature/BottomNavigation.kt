package ir.heyatyab.presentation.feature

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import ir.heyatyab.R
import ir.heyatyab.presentation.theme.HeyatYabTheme
import ir.heyatyab.presentation.theme.LocalCustomColors

sealed class BottomNavigationItem(
    @DrawableRes val iconSelectedRes: Int,
    @DrawableRes val iconUnselectedRes: Int,
    @StringRes val labelRes: Int,
    val route: String
) {
    data object Home : BottomNavigationItem(
        R.drawable.ic_home_fill,
        R.drawable.ic_home_outline,
        R.string.home,
        "home_root"
    )

    data object Profile : BottomNavigationItem(
        R.drawable.ic_profile_fill,
        R.drawable.ic_profile_outline,
        R.string.profile,
        "profile_root"
    )
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>,
    currentRoute: String = BottomNavigationItem.Home.route,
    onItemSelected: (BottomNavigationItem) -> Unit
) {
    val colors = LocalCustomColors.current

    Column {
        Divider(Modifier
            .fillMaxWidth()
            .height(1.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surface)
                .padding(vertical = 10.dp, horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items.forEach { item ->
                val selected = item.route == currentRoute
                val backgroundColor =
                    if (selected) colors.primary.copy(alpha = 0.15f) else Color.Transparent
                val tintIcon = if (selected) colors.primary else colors.textSecondary

                val icon = painterResource(
                    id = if (selected) item.iconSelectedRes else item.iconUnselectedRes
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 5.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(backgroundColor)
                        .clickable {
                            onItemSelected(item)
                        }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center

                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = icon,
                            contentDescription = item.route,
                            modifier = Modifier.size(24.dp),
                            tint = tintIcon
                        )

                        AnimatedVisibility(visible = selected) {
                            Text(
                                text = stringResource(item.labelRes),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 2.dp),
                                color = colors.primary ,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                }
            }
        }
    }


}

@Preview
@Composable
private fun BottomNavigationBarPreview() {
    HeyatYabTheme {
        BottomNavigationBar(
            items = listOf(
                BottomNavigationItem.Home,
                BottomNavigationItem.Profile
            )
        ) { }
    }
}