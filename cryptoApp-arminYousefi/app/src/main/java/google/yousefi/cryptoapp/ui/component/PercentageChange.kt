package google.yousefi.cryptoapp.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import google.yousefi.cryptoapp.model.Percentage
import google.yousefi.cryptoapp.ui.previewdata.PercentagePreviewProvider
import google.yousefi.cryptoapp.ui.theme.AppTheme
import google.yousefi.cryptoapp.ui.theme.NegativeRed
import google.yousefi.cryptoapp.ui.theme.PositiveGreen

/**
 * این Composable برای نمایش درصد تغییر با رنگ مخصوص استفاده می‌شود.
 *
 * @param percentage شیء Percentage که حاوی مقدار درصد و سایر ویژگی‌های آن است.
 * @param modifier Modifier برای تنظیمات ویژگی‌های ظاهری Composable.
 */
@Composable
fun PercentageChange(
    percentage: Percentage,
    modifier: Modifier = Modifier
) {
    val textColor = when {
        percentage.isPositive -> PositiveGreen
        percentage.isNegative -> NegativeRed
        else -> MaterialTheme.colorScheme.onSurface
    }

    Text(
        text = percentage.formattedAmount,
        style = MaterialTheme.typography.bodyMedium,
        color = textColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

/**
 * پیش‌نمایش برای PercentageChange به منظور تست نمایش صحیح آن در AppTheme.
 *
 * @param percentage پارامتر پیش‌نمایش که از PercentagePreviewProvider تأمین می‌شود.
 */
@Composable
@Preview
private fun PercentageChangePreview(
    @PreviewParameter(PercentagePreviewProvider::class) percentage: Percentage
) {
    AppTheme {
        PercentageChange(
            percentage = percentage
        )
    }
}
