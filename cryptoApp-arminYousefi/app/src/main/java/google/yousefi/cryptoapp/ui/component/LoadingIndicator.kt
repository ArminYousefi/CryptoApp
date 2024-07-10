package google.yousefi.cryptoapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import google.yousefi.cryptoapp.ui.theme.AppTheme

/**
 * این Composable برای نمایش یک نمادگذار پیشرفت بارگذاری استفاده می‌شود.
 *
 * @param modifier Modifier برای تنظیمات ویژگی‌های ظاهری Composable.
 */
@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onBackground,
            strokeWidth = 5.dp,
            strokeCap = StrokeCap.Round
        )
    }
}

/**
 * پیش‌نمایش برای LoadingIndicator به منظور تست نمایش صحیح آن در AppTheme.
 */
@Preview(showBackground = true)
@Composable
private fun LoadingIndicatorPreview() {
    AppTheme {
        LoadingIndicator()
    }
}
