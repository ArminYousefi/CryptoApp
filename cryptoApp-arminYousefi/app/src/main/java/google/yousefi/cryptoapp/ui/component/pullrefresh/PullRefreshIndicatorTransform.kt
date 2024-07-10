package google.yousefi.cryptoapp.ui.component.pullrefresh



import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable

/**
 * یک اصلاح‌گر برای ترجمه موقعیت و مقیاس‌دهی اندازه نشانگر کشیدن-برای-تازه‌سازی
 * بر اساس [PullRefreshState] داده شده.
 *
 * @sample androidx.compose.material.samples.PullRefreshIndicatorTransformSample
 *
 * @param state [PullRefreshState] که موقعیت نشانگر را تعیین می‌کند.
 * @param scale یک مقدار boolean که کنترل می‌کند آیا اندازه نشانگر با پیشرفت کشیدن مقیاس می‌شود یا خیر.
 */
fun Modifier.pullRefreshIndicatorTransform(
    state: PullRefreshState,
    scale: Boolean = false
) = inspectable(
    inspectorInfo = debugInspectorInfo {
        name = "pullRefreshIndicatorTransform"
        properties["state"] = state
        properties["scale"] = scale
    }
) {
    Modifier
        .drawWithContent {
            clipRect(
                top = 0f,
                left = -Float.MAX_VALUE,
                right = Float.MAX_VALUE,
                bottom = Float.MAX_VALUE
            ) {
                this@drawWithContent.drawContent()
            }
        }
        .graphicsLayer {
            translationY = state.position - size.height

            if (scale && !state.refreshing) {
                val scaleFraction = LinearOutSlowInEasing
                    .transform(state.position / state.threshold)
                    .coerceIn(0f, 1f)
                scaleX = scaleFraction
                scaleY = scaleFraction
            }
        }
}
