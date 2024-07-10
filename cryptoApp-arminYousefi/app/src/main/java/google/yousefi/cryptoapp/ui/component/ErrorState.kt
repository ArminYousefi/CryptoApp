package google.yousefi.cryptoapp.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import google.yousefi.cryptoapp.R
import google.yousefi.cryptoapp.ui.theme.AppTheme

/**
 * این Composable برای نمایش وضعیت خطا با تصویر، عنوان خطا و پیام خطا استفاده می‌شود.
 *
 * @param message پیام خطا که نمایش داده می‌شود.
 * @param modifier Modifier برای تنظیمات ویژگی‌های ظاهری Composable.
 */
@Composable
fun ErrorState(
    message: String?,
    modifier: Modifier = Modifier
) {
    EmptyState(
        image = painterResource(R.drawable.error_state),
        title = stringResource(R.string.error_occurred),
        subtitle = {
            message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        modifier = modifier
    )
}

/**
 * پیش‌نمایش برای ErrorState به منظور تست نمایش صحیح آن در AppTheme.
 */
@Composable
@Preview(heightDp = 400)
private fun ErrorStatePreview() {
    AppTheme {
        ErrorState(
            message = "No internet connection",
        )
    }
}
