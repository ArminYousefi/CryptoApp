package google.yousefi.cryptoapp.data.source.local.preferences.global

import google.yousefi.cryptoapp.data.source.local.preferences.BasePreferencesSerializer

/**
 * یک شیء سریال‌سازی برای تنظیمات ترجیحات کاربر استفاده می‌شود، که از کلاس پایه BasePreferencesSerializer ارث‌بری می‌کند.
 *
 * @property defaultInstance تابعی که نمونه پیش‌فرض از نوع UserPreferences را ایجاد می‌کند.
 * @property serializer سریال‌سازی است که برای سریال‌سازی و دسریال‌سازی تنظیمات از نوع UserPreferences استفاده می‌شود.
 */
object UserPreferencesSerializer : BasePreferencesSerializer<UserPreferences>(
    defaultInstance = { UserPreferences() },
    serializer = UserPreferences.serializer(),
)
