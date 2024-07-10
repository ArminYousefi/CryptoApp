package google.yousefi.cryptoapp.data.source.local.preferences.market

import google.yousefi.cryptoapp.data.source.local.preferences.BasePreferencesSerializer

/**
 * یک شیء سریال‌سازی برای تنظیمات بازار استفاده می‌شود، که از کلاس پایه BasePreferencesSerializer ارث‌بری می‌کند.
 *
 * @property defaultInstance تابعی که نمونه پیش‌فرض از نوع MarketPreferences را ایجاد می‌کند.
 * @property serializer سریال‌سازی است که برای سریال‌سازی و دسریال‌سازی تنظیمات از نوع MarketPreferences استفاده می‌شود.
 */
object MarketPreferencesSerializer : BasePreferencesSerializer<MarketPreferences>(
    defaultInstance = { MarketPreferences() },
    serializer = MarketPreferences.serializer()
)

