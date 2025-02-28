package google.yousefi.cryptoapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.CurrencyBitcoin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import google.yousefi.cryptoapp.ui.theme.AppTheme

/**
 * این Composable برای نمایش یک پنل پایینی مدال استفاده می‌شود.
 *
 * @param title عنوان پنل پایینی مدال.
 * @param onDismissRequest تابعی که فراخوانی می‌شود هنگام بسته شدن پنل پایینی مدال.
 * @param sheetState وضعیت پنل پایینی مدال.
 * @param modifier Modifier برای تنظیمات ویژگی‌های ظاهری Composable.
 * @param content محتوایی که درون پنل پایینی مدال نمایش داده می‌شود.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    title: String,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        dragHandle = null,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 0.dp,
        sheetState = sheetState,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(16.dp))

            content()
        }
    }
}

/**
 * این Composable برای نمایش گزینه‌های مختلف درون پنل پایینی مدال استفاده می‌شود.
 *
 * @param icon آیکون مربوط به گزینه.
 * @param label برچسب متنی گزینه.
 * @param isSelected وضعیت انتخاب شدن گزینه.
 * @param onSelected تابعی که فراخوانی می‌شود هنگام انتخاب گزینه.
 * @param modifier Modifier برای تنظیمات ویژگی‌های ظاهری Composable.
 */
@Composable
fun BottomSheetOption(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val optionBackgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .background(optionBackgroundColor)
            .selectable(selected = isSelected, onClick = onSelected, role = Role.RadioButton)
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/**
 * پیش‌نمایش برای BottomSheetOption به منظور تست نمایش صحیح آن در AppTheme.
 */
@Composable
@Preview
private fun BottomSheetOptionPreview() {
    AppTheme {
        Column {
            BottomSheetOption(
                icon = Icons.Rounded.CurrencyBitcoin,
                label = "Bitcoin",
                isSelected = true,
                onSelected = {}
            )
            BottomSheetOption(
                icon = Icons.Rounded.AttachMoney,
                label = "USD",
                isSelected = false,
                onSelected = {}
            )
        }
    }
}
