package google.yousefi.cryptoapp.ui.component.pullrefresh



import androidx.compose.animation.core.animate
import androidx.compose.foundation.MutatorMutex
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.pow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * یک [PullRefreshState] ایجاد می‌کند که بین ترکیب‌ها به یاد می‌ماند.
 *
 * تغییرات در [refreshing] منجر به به‌روزرسانی [PullRefreshState] خواهد شد.
 *
 * @sample androidx.compose.material.samples.PullRefreshSample
 *
 * @param refreshing یک مقدار boolean که نشان می‌دهد آیا تازه‌سازی در حال وقوع است یا خیر.
 * @param onRefresh تابعی که برای ایجاد تازه‌سازی فراخوانی می‌شود.
 * @param refreshThreshold حد آستانه‌ای که اگر رهاسازی در زیر آن رخ دهد، [onRefresh] فراخوانی خواهد شد.
 * @param refreshingOffset افستی که نشانگر در حال تازه‌سازی در آن ترسیم خواهد شد. این افست
 * مربوط به موقعیت پایین نشانگر است.
 */

@Composable
fun rememberPullRefreshState(
    refreshing: Boolean,
    onRefresh: () -> Unit,
    refreshThreshold: Dp = PullRefreshDefaults.RefreshThreshold,
    refreshingOffset: Dp = PullRefreshDefaults.RefreshingOffset
): PullRefreshState {
    require(refreshThreshold > 0.dp) { "The refresh trigger must be greater than zero!" }

    val scope = rememberCoroutineScope()
    val onRefreshState = rememberUpdatedState(onRefresh)
    val thresholdPx: Float
    val refreshingOffsetPx: Float

    with(LocalDensity.current) {
        thresholdPx = refreshThreshold.toPx()
        refreshingOffsetPx = refreshingOffset.toPx()
    }

    val state = remember(scope) {
        PullRefreshState(scope, onRefreshState, refreshingOffsetPx, thresholdPx)
    }

    SideEffect {
        state.setRefreshing(refreshing)
        state.setThreshold(thresholdPx)
        state.setRefreshingOffset(refreshingOffsetPx)
    }

    return state
}

/**
 * یک شیء وضعیت که می‌تواند به همراه [pullRefresh] برای اضافه کردن رفتار کشیدن-برای-تازه‌سازی
 * به یک مؤلفه پیمایش استفاده شود. بر اساس SwipeRefreshLayout اندروید.
 *
 * [progress] را فراهم می‌کند، یک مقدار float که نشان می‌دهد کاربر به چه میزان به عنوان درصدی
 * از refreshThreshold کشیده است. مقادیر برابر یا کمتر از یک نشان می‌دهد که کاربر هنوز از
 * آستانه عبور نکرده است. مقادیر بیشتر از یک نشان می‌دهد که کاربر تا چه حد از آستانه عبور کرده است.
 *
 * می‌تواند به همراه [pullRefreshIndicatorTransform] برای پیاده‌سازی رفتار کشیدن-برای-تازه‌سازی
 * مشابه اندروید با یک نشانگر سفارشی استفاده شود.
 *
 * باید با استفاده از [rememberPullRefreshState] ایجاد شود.
 */
class PullRefreshState internal constructor(
    private val animationScope: CoroutineScope,
    private val onRefreshState: State<() -> Unit>,
    refreshingOffset: Float,
    threshold: Float
) {
    /**
     * یک مقدار float که نشان می‌دهد کاربر به چه میزان به عنوان درصدی از refreshThreshold کشیده است.
     *
     * اگر مؤلفه به هیچ وجه کشیده نشده باشد، progress برابر با صفر است. اگر کشیدن به نیمه راه
     * آستانه رسیده باشد، progress برابر با 0.5f است. مقداری بیشتر از 1 نشان می‌دهد که کشیدن از
     * refreshThreshold فراتر رفته است - به عنوان مثال، مقداری برابر با 2f نشان می‌دهد که کاربر
     * تا دو برابر refreshThreshold کشیده است.
     */
    val progress get() = adjustedDistancePulled / threshold

    internal val refreshing get() = _refreshing
    internal val position get() = _position
    internal val threshold get() = _threshold

    private val adjustedDistancePulled by derivedStateOf { distancePulled * DragMultiplier }

    private var _refreshing by mutableStateOf(false)
    private var _position by mutableFloatStateOf(0f)
    private var distancePulled by mutableFloatStateOf(0f)
    private var _threshold by mutableFloatStateOf(threshold)
    private var _refreshingOffset by mutableFloatStateOf(refreshingOffset)

    internal fun onPull(pullDelta: Float): Float {
        if (_refreshing) return 0f

        val newOffset = (distancePulled + pullDelta).coerceAtLeast(0f)
        val dragConsumed = newOffset - distancePulled
        distancePulled = newOffset
        _position = calculateIndicatorPosition()
        return dragConsumed
    }

    internal fun onRelease(velocity: Float): Float {
        if (refreshing) return 0f

        if (adjustedDistancePulled > threshold) {
            onRefreshState.value()
        }
        animateIndicatorTo(0f)
        val consumed = when {
            distancePulled == 0f -> 0f
            velocity < 0f -> 0f
            else -> velocity
        }
        distancePulled = 0f
        return consumed
    }

    internal fun setRefreshing(refreshing: Boolean) {
        if (_refreshing != refreshing) {
            _refreshing = refreshing
            distancePulled = 0f
            animateIndicatorTo(if (refreshing) _refreshingOffset else 0f)
        }
    }

    internal fun setThreshold(threshold: Float) {
        _threshold = threshold
    }

    internal fun setRefreshingOffset(refreshingOffset: Float) {
        if (_refreshingOffset != refreshingOffset) {
            _refreshingOffset = refreshingOffset
            if (refreshing) animateIndicatorTo(refreshingOffset)
        }
    }


    private val mutatorMutex = MutatorMutex()

    private fun animateIndicatorTo(offset: Float) = animationScope.launch {
        mutatorMutex.mutate {
            animate(initialValue = _position, targetValue = offset) { value, _ ->
                _position = value
            }
        }
    }

    private fun calculateIndicatorPosition(): Float = when {
        adjustedDistancePulled <= threshold -> adjustedDistancePulled
        else -> {
            val overshootPercent = abs(progress) - 1.0f
            val linearTension = overshootPercent.coerceIn(0f, 2f)
            val tensionPercent = linearTension - linearTension.pow(2) / 4
            val extraOffset = threshold * tensionPercent
            threshold + extraOffset
        }
    }
}


object PullRefreshDefaults {

    val RefreshThreshold = 80.dp


    val RefreshingOffset = 56.dp
}

private const val DragMultiplier = 0.5f
