package google.yousefi.cryptoapp.ui.screen.favourites.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import google.yousefi.cryptoapp.R
import google.yousefi.cryptoapp.ui.component.EmptyState
import google.yousefi.cryptoapp.ui.theme.AppTheme

@Composable
fun FavouritesEmptyState(modifier: Modifier = Modifier) {
    EmptyState(
        image = painterResource(R.drawable.empty_state_favourites),
        title = stringResource(R.string.empty_state_favourite_coins_title),
        subtitle = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.empty_state_favourite_coins_subtitle_start),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Icon(
                    imageVector = Icons.Rounded.FavoriteBorder,
                    contentDescription = stringResource(R.string.cd_top_bar_favourite),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(start = 4.dp, top = 2.dp, end = 4.dp)
                )

                Text(
                    text = stringResource(R.string.empty_state_favourite_coins_subtitle_end),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        modifier = modifier
    )
}

@Composable
@Preview(heightDp = 400)
private fun FavouritesEmptyStatePreview() {
    AppTheme {
        FavouritesEmptyState()
    }
}
