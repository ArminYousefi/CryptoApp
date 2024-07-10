package google.yousefi.cryptoapp.data.source.local.preferences.global

import androidx.annotation.StringRes
import google.yousefi.cryptoapp.R
import kotlinx.serialization.Serializable

/**
 * یک کلاس داده‌ای که ترجیحات کاربر را نگهداری می‌کند، شامل ارز پیش‌فرض و صفحه شروع.
 *
 * @property currency ارز پیش‌فرض که توسط enum Currency تعیین می‌شود.
 * @property startScreen صفحه شروع پیش‌فرض که توسط enum StartScreen تعیین می‌شود.
 */
@Serializable
data class UserPreferences(
    val currency: Currency = Currency.USD,
    val startScreen: StartScreen = StartScreen.Market,
)

/**
 * یک enum برای نمایش ارزهای مختلف با نماد و نام مرتبط.
 *
 * @property symbol نماد ارز.
 * @property nameId رشته‌ی منابع مورد استفاده برای نام این ارز.
 */
enum class Currency(val symbol: String, @StringRes val nameId: Int) {
    USD("$", R.string.currency_usd),
    GBP("£", R.string.currency_gbp),
    EUR("€", R.string.currency_eur)
}

/**
 * یک enum برای نمایش صفحه شروع‌های مختلف در اپلیکیشن.
 *
 * @property nameId رشته‌ی منابع مورد استفاده برای نام این صفحه شروع.
 */
enum class StartScreen(@StringRes val nameId: Int) {
    Market(R.string.market_screen),
    Favourites(R.string.favourites_screen),
    Search(R.string.search_screen)
}

