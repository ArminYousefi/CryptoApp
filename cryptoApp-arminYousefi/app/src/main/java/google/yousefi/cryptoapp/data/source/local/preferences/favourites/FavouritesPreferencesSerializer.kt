package google.yousefi.cryptoapp.data.source.local.preferences.favourites

import google.yousefi.cryptoapp.data.source.local.preferences.BasePreferencesSerializer

/**
 * یک شیء سریال‌سازی برای تنظیمات لیست علاقه‌مندی‌ها استفاده می‌شود، که از کلاس پایه BasePreferencesSerializer ارث‌بری می‌کند.
 *
 * @property defaultInstance تابعی که نمونه پیش‌فرض از نوع FavouritesPreferences را ایجاد می‌کند.
 * @property serializer سریال‌سازی است که برای سریال‌سازی و دسریال‌سازی تنظیمات از نوع FavouritesPreferences استفاده می‌شود.
 */
object FavouritesPreferencesSerializer : BasePreferencesSerializer<FavouritesPreferences>(
    defaultInstance = { FavouritesPreferences() },
    serializer = FavouritesPreferences.serializer(),
)
