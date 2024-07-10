package google.yousefi.cryptoapp.ui.component.pullrefresh


import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.Drag
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.unit.Velocity

/**
 * یک اصلاح‌گر پیمایش تو در تو که رویدادهای پیمایش را به [state] ارائه می‌دهد.
 *
 * توجه داشته باشید که این اصلاح‌گر باید بالای یک محفظه پیمایشی، مانند یک ستون تنبل،
 * اضافه شود تا بتواند رویدادهای پیمایش را دریافت کند. به عنوان مثال:
 *
 * @sample androidx.compose.material.samples.PullRefreshSample
 *
 * @param state [PullRefreshState] مرتبط با این مؤلفه کشیدن-برای-تازه‌سازی.
 * این اصلاح‌گر، وضعیت را به‌روزرسانی خواهد کرد.
 * @param enabled اگر غیرفعال باشد، تمام دلتاهای پیمایش و سرعت پرتاب نادیده گرفته می‌شوند.
 */
fun Modifier.pullRefresh(
    state: PullRefreshState,
    enabled: Boolean = true
) = inspectable(
    inspectorInfo = debugInspectorInfo {
        name = "pullRefresh"
        properties["state"] = state
        properties["enabled"] = enabled
    }
) {
    Modifier.pullRefresh(state::onPull, state::onRelease, enabled)
}

/**
 * یک اصلاح‌گر پیمایش تو در تو که بازخوانی‌های [onPull] و [onRelease] را برای ساخت مؤلفه‌های
 * تازه‌سازی کششی سفارشی فراهم می‌کند.
 *
 * توجه داشته باشید که این اصلاح‌گر باید بالای یک محفظه پیمایشی، مانند یک ستون تنبل،
 * اضافه شود تا بتواند رویدادهای پیمایش را دریافت کند. به عنوان مثال:
 *
 * @sample androidx.compose.material.samples.CustomPullRefreshSample
 *
 * @param onPull بازخوانی برای ارسال دلتاهای پیمایش عمودی، که float pullDelta را به عنوان
 * آرگومان می‌گیرد. دلتا مثبت (کشیدن به پایین) تنها زمانی ارسال می‌شود که فرزند آن را
 * مصرف نکند (یعنی کشیدن به پایین با وجود قرار داشتن در بالای یک مؤلفه قابل پیمایش)،
 * در حالی که دلتا منفی (کشیدن به بالا) ابتدا ارسال می‌شود (در صورتی که نیاز باشد
 * شاخص را به بالا برگرداند)، و سپس دلتا مصرف نشده به فرزند ارسال می‌شود. بازخوانی نشان
 * می‌دهد که چقدر دلتا مصرف شده است.
 * @param onRelease بازخوانی برای زمانی که کشیدن رها می‌شود، که float flingVelocity را به
 * عنوان آرگومان می‌گیرد. بازخوانی نشان می‌دهد که چقدر سرعت مصرف شده است - در اکثر
 * موارد این باید تنها زمانی سرعت را مصرف کند که تازه‌سازی کششی قبلاً کشیده شده و سرعت
 * مثبت است (پرش به پایین است)، زیرا یک پرش به بالا باید معمولاً همچنان یک مؤلفه قابل
 * پیمایش زیر تازه‌سازی کششی را پیمایش کند. این قبل از ارسال هر سرعت باقی‌مانده به فرزند
 * اجرا می‌شود.
 * @param enabled اگر غیرفعال باشد، تمام دلتاهای پیمایش و سرعت پرتاب نادیده گرفته می‌شوند
 * و نه [onPull] و نه [onRelease] فراخوانی نخواهند شد.
 */
fun Modifier.pullRefresh(
    onPull: (pullDelta: Float) -> Float,
    onRelease: suspend (flingVelocity: Float) -> Float,
    enabled: Boolean = true
) = inspectable(
    inspectorInfo = debugInspectorInfo {
        name = "pullRefresh"
        properties["onPull"] = onPull
        properties["onRelease"] = onRelease
        properties["enabled"] = enabled
    }
) {
    Modifier.nestedScroll(PullRefreshNestedScrollConnection(onPull, onRelease, enabled))
}

private class PullRefreshNestedScrollConnection(
    private val onPull: (pullDelta: Float) -> Float,
    private val onRelease: suspend (flingVelocity: Float) -> Float,
    private val enabled: Boolean
) : NestedScrollConnection {

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset = when {
        !enabled -> Offset.Zero
        source == Drag && available.y < 0 -> Offset(0f, onPull(available.y)) // Swiping up
        else -> Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = when {
        !enabled -> Offset.Zero
        source == Drag && available.y > 0 -> Offset(0f, onPull(available.y)) // Pulling down
        else -> Offset.Zero
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        return Velocity(0f, onRelease(available.y))
    }
}
