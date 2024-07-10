package google.yousefi.cryptoapp.ui.screen.settings

import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.local.preferences.global.StartScreen

/**
 * وضعیت UI برای صفحه‌ی تنظیمات شامل تنظیمات مختلف مانند ارزش، نمایش وضعیت ارزش، صفحه‌ی شروع و نمایش وضعیت صفحه‌ی شروع.
 *
 * @property currency ارزش جاری
 * @property isCurrencySheetShown وضعیت نمایش وضعیت ارزش
 * @property startScreen صفحه‌ی شروع جاری
 * @property isStartScreenSheetShown وضعیت نمایش وضعیت صفحه‌ی شروع
 * @property isLoading وضعیت بارگذاری داده‌ها
 * @property errorMessage پیغام خطا در صورت وجود
 */
data class SettingsUiState(
    val currency: Currency = Currency.USD,
    val isCurrencySheetShown: Boolean = false,
    val startScreen: StartScreen = StartScreen.Market,
    val isStartScreenSheetShown: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
