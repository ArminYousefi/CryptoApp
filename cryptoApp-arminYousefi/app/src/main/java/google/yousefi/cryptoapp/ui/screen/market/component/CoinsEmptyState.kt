package google.yousefi.cryptoapp.ui.screen.market.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import google.yousefi.cryptoapp.R
import google.yousefi.cryptoapp.ui.component.EmptyState
import google.yousefi.cryptoapp.ui.theme.AppTheme

@Composable
fun CoinsEmptyState(modifier: Modifier = Modifier) {
    EmptyState(
        image = painterResource(R.drawable.empty_state_coins),
        title = stringResource(R.string.empty_state_coins_title),
        subtitle = {
            Text(
                text = stringResource(R.string.empty_state_coins_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = modifier
    )
}

@Composable
@Preview(heightDp = 400)
private fun CoinsEmptyStatePreview() {
    AppTheme {
        CoinsEmptyState()
    }
}
