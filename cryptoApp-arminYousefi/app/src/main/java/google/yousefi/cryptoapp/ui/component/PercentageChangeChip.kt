package google.yousefi.cryptoapp.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import google.yousefi.cryptoapp.model.Percentage
import google.yousefi.cryptoapp.ui.previewdata.PercentagePreviewProvider
import google.yousefi.cryptoapp.ui.theme.AppTheme
import google.yousefi.cryptoapp.ui.theme.NegativeRed
import google.yousefi.cryptoapp.ui.theme.PositiveGreen

/**
 * این Composable برای نمایش یک تراشه با درصد تغییر و رنگ مخصوص استفاده می‌شود.
 *
 * @param percentage شیء Percentage که حاوی مقدار درصد و سایر ویژگی‌های آن است.
 * @param modifier Modifier برای تنظیمات ویژگی‌های ظاهری Composable.
 */
@Composable
fun PercentageChangeChip(
    percentage: Percentage,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        percentage.isPositive -> PositiveGreen
        percentage.isNegative -> NegativeRed
        else -> MaterialTheme.colorScheme.background
    }

    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        color = backgroundColor
    ) {
        Text(
            text = percentage.formattedAmount,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White,
            modifier = modifier.padding(vertical = 1.dp, horizontal = 6.dp)
        )
    }
}

/**
 * پیش‌نمایش برای PercentageChangeChip به منظور تست نمایش صحیح آن در AppTheme.
 *
 * @param percentage پارامتر پیش‌نمایش که از PercentagePreviewProvider تأمین می‌شود.
 */
@Composable
@Preview(showBackground = true)
private fun PercentageChangeChipPreview(
    @PreviewParameter(PercentagePreviewProvider::class) percentage: Percentage
) {
    AppTheme {
        PercentageChangeChip(
            percentage = percentage
        )
    }
}
